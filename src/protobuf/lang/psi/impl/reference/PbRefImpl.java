package protobuf.lang.psi.impl.reference;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.PsiElementFactoryImpl;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import protobuf.lang.PbElementTypes;
import protobuf.lang.PbTokenTypes;
import protobuf.lang.psi.PbPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.declaration.PbExtendDef;
import protobuf.lang.psi.api.declaration.PbGroupDef;
import protobuf.lang.psi.api.declaration.PbImportDef;
import protobuf.lang.psi.api.declaration.PbPackageDef;
import protobuf.lang.psi.api.declaration.PbServiceMethodDef;
import protobuf.lang.psi.api.member.PbFieldType;
import protobuf.lang.psi.api.member.PbOptionRefSeq;
import protobuf.lang.psi.api.reference.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.utils.PbPsiUtil;
import protobuf.lang.resolve.PbResolveUtil;
import protobuf.util.PbFileUtil;
import protobuf.util.PbTextUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static protobuf.lang.PbElementTypes.CLOSE_PARENTHESIS;
import static protobuf.lang.PbElementTypes.DOT;
import static protobuf.lang.PbElementTypes.IK;
import static protobuf.lang.psi.PbPsiEnums.CompletionKind;
import static protobuf.lang.psi.PbPsiEnums.ReferenceKind;

/**
 * @author Nikolay Matveev
 * Date: Mar 30, 2010
 */
public class PbRefImpl extends PbPsiElementImpl implements PbRef {

    private final static Logger LOG = Logger.getInstance(PbRefImpl.class.getName());

    private static final ExperimentalResolver expResolver = new ExperimentalResolver();
    
    private static ResolveCache resolveCache = null;

    @Override
    public String toString() {
        switch (getRefKind()) {
            case PACKAGE: {
                return "PACKAGE_REF";
            }
            case FILE: {
                return "FILE_REF";
            }
            case MESSAGE_OR_GROUP: {
                return "MESSAGE_OR_GROUP_REF";
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {
                return "MESSAGE_OR_ENUM_GROUP_REF";
            }
            case MESSAGE_OR_PACKAGE_OR_GROUP: {
                return "MESSAGE_OR_PACKAGE_OR_GROUP_REF";
            }
            case MESSAGE_OR_GROUP_FIELD: {
                return "MESSAGE_OR_GROUP_FIELD_REF";
            }
            case EXTEND_FIELD: {
                return "EXTEND_FIELD_REF";
            }
        }
        assert false;
        return null;
    }

    public PbRefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull PbPsiElementVisitor visitor) {
        visitor.visitRef(this);
    }

    @Override
    public PsiReference getReference() {
        return this;
    }

    @Override
    public PbFile getContainingFile() {
        return (PbFile) super.getContainingFile();
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @Nullable
    @Override
    public PsiElement getQualifier() {
        PbRef ref = getQualifierRef(); // Call into the old method for finding the qualifying reference.
        PsiElement refEl = ref != null ? ref.resolve() : null; // If the reference exists, resolve its element.
        return refEl;
    }

    /**
     * The #getQualifier() method signature changed in Idea 12 to return a PsiElement.  This method simply maintains
     * the pre-existing behavior to find the qualifying PbRef element.
     * @return the qualifying ref
     */
    @Nullable
    public PbRef getQualifierRef() {
        switch (getRefKind()) {
            case FILE: {
                return null;
            }
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:
            case EXTEND_FIELD: {
                return findChildByClass(PbRef.class);
            }
        }
        assert false;
        return null;
    }

    @Override
    public String getReferenceName() {
        PsiElement psi = findChildByType(PbTokenTypes.IK);
        return psi != null ? psi.getText() : null;
    }

    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        switch (getRefKind()) {
            case FILE: {
            }
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:
            case EXTEND_FIELD: {
                PsiElement refNameElement = findChildByType(IK);
                if (refNameElement != null) {
                    final int offsetInParent = refNameElement.getStartOffsetInParent();
                    return new TextRange(offsetInParent, offsetInParent + refNameElement.getTextLength());
                }

            }
            break;
        }
        return new TextRange(0, getTextLength());
    }

    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public PsiElement handleElementRename(final String newName) throws IncorrectOperationException {
        PsiElement nameElement = getReferenceNameElement();
        if (nameElement != null) {
            if(getRefKind() == ReferenceKind.MESSAGE_OR_GROUP_FIELD && (resolve() instanceof PbGroupDef)){
                nameElement.getNode().getTreeParent().replaceChild(nameElement.getNode(),PbPsiUtil.createSimpleNodeWithText(newName.toLowerCase(),getProject()));
            }else{
                nameElement.getNode().getTreeParent().replaceChild(nameElement.getNode(),PbPsiUtil.createSimpleNodeWithText(newName,getProject()));
            }
            return this;
        }
        throw new IncorrectOperationException();
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        final IElementType elementType = getNode().getElementType();
        if ((elementType == PbElementTypes.IMPORT_DECL || elementType == PbElementTypes.IMPORT_REF) && psiElement instanceof PsiFile) {
            // The file to which the import reference points is moving.  Get the new path and change the import reference
            // text to the new relative path to the file.

            final String newFilePath = ((PsiFile)psiElement).getVirtualFile().getPath();
            String pathToContainingFile = PathUtil.getParentPath(getContainingFile().getVirtualFile().getPath());
            String relativePathToContainingFile = FileUtil.getRelativePath(pathToContainingFile, newFilePath, File.separatorChar);
            if (null == relativePathToContainingFile) {
                return null;
            }

            PsiElementFactory factory = new PsiElementFactoryImpl(getManager());
            /*
            // The parser has a problem with parsing just simple string literals in that they don't fall into any of the
            // element patterns it expects.  With no context, the string literal falls through to an error handler and
            // gets marked with an error annotation.
            // To make a long story short, it's easier "for now" to construct a full import statement and replace the
            // import declaration element than to fix the parser.
            // - TC
            PsiElement newEl = factory.createDummyHolder(("\"" + relativePathToContainingFile + "\""), elementType, getContext());
            return this.replace(newEl);
            */

            PsiElement newEl = factory.createDummyHolder(("import \"" + relativePathToContainingFile + "\";"), PbElementTypes.IMPORT_DECL, getContext());
            newEl = newEl.getFirstChild(); // The first child of the dummy holder is the element we need.
            PsiElement newImportEl = this.getParent().replace(newEl);
            PsiElement newImportRefEl = newImportEl.getFirstChild();
            return newImportRefEl;
        }

        throw new IncorrectOperationException("Cannot bind to element: " + psiElement);
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return getManager().areElementsEquivalent(psiElement, resolve());
    }

    @Override
    public PsiElement resolve() {
        PsiElement el = null;
        ResolveCache rc = ResolveCache.getInstance(getProject());
        if (rc != null) {
            el = rc.resolveWithCaching(this, expResolver, true, false);
        } else {
            LOG.error("ResolveCache was not found.  Could not resolve element.");
        }
        return el;
    }

    @Override
    public String getCanonicalText() {
        switch (getRefKind()) {
            case FILE: {
            }
            case PACKAGE: {
            }
            case MESSAGE_OR_GROUP: {
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {

            }
            case MESSAGE_OR_PACKAGE_OR_GROUP: {

            }
            case MESSAGE_OR_GROUP_FIELD: {

            }
            case EXTEND_FIELD: {

            }
        }
        if (getRefKind().equals(ReferenceKind.FILE)) {
            return PbTextUtil.trim(getText(), '"');
        }
        return null;
    }

    @Override
    public boolean isLastInChainReference() {
        return !(getParent() instanceof PbRef);
    }

    public PsiElement getReferenceNameElement() {
        switch (getRefKind()) {
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:
            case EXTEND_FIELD: {
                return findChildByType(PbTokenTypes.IK);
            }
        }
        return null;
    }

    public ReferenceKind getRefKind() {
        //todo caching kind
        PsiElement parent = getParent();
        if (parent instanceof PbRef) {
            ReferenceKind parentKind = ((PbRefImpl) parent).getRefKind();
            assert parentKind != null;
            switch (parentKind) {
                case FILE: {
                    return ReferenceKind.FILE;
                }
                case PACKAGE: {
                    return ReferenceKind.PACKAGE;
                }
                case MESSAGE_OR_GROUP: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case MESSAGE_OR_ENUM_OR_GROUP: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case MESSAGE_OR_PACKAGE_OR_GROUP: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case EXTEND_FIELD: {
                    return ReferenceKind.MESSAGE_OR_PACKAGE_OR_GROUP;
                }
                case MESSAGE_OR_GROUP_FIELD: {
                    if (findChildByType(CLOSE_PARENTHESIS) != null) {
                        return ReferenceKind.EXTEND_FIELD;
                    }
                    return ReferenceKind.MESSAGE_OR_GROUP_FIELD;
                }
                default: {
                    assert false;
                }
            }

        }
        if (parent instanceof PbExtendDef) {
            return ReferenceKind.MESSAGE_OR_GROUP;
        }
        if (parent instanceof PbFieldType) {
            return ReferenceKind.MESSAGE_OR_ENUM_OR_GROUP;
        }
        if (parent instanceof PbOptionRefSeq) {
            if (findChildByType(CLOSE_PARENTHESIS) != null) {
                return ReferenceKind.EXTEND_FIELD;
            }
            return ReferenceKind.MESSAGE_OR_GROUP_FIELD;
        }
        if (parent instanceof PbPackageDef) {
            return ReferenceKind.PACKAGE;
        }
        if (parent instanceof PbImportDef) {
            return ReferenceKind.FILE;
        }
        //todo 'enum constants as value of options and fields', advanced imports

        if (parent instanceof PbServiceMethodDef) {
            return ReferenceKind.MESSAGE_OR_GROUP;
        }
        return null;
    }

    public CompletionKind getCompletionKind() {
        //todo complete
        return null;
    }

    public static class ExperimentalResolver implements ResolveCache.Resolver {

        public PsiElement resolve(PsiReference refElement, boolean incompleteCode) {
            final PbRefImpl ref = (PbRefImpl) refElement;
            final ReferenceKind refKind = ref.getRefKind();
            final PbRef qualifier = ref.getQualifierRef();
            switch (refKind) {
                case FILE: {
                    //todo
                    final String relativePath = PbTextUtil.trim(ref.getText(),'\"');                    
                    if (relativePath.length() == 0) {
                        return null;
                    }
                    if (PbFileUtil.isPathToDirectory(relativePath)) {
                        return null;
                    }
                    final String fileName = new File(relativePath).getName();

                    //hack for test code
                    PsiFile psiFile = null;
                    PsiFile[] foundFiles = FilenameIndex.getFilesByName(ref.getProject(), fileName, ref.getResolveScope());
                    for (PsiFile foundFile : foundFiles) {
                        if (foundFile.getVirtualFile().getPath().equals(File.separator + relativePath)) {
                            psiFile = foundFile;
                        }
                    }
                    if (psiFile != null) return psiFile; //todo avoid such hacking

                    //real code
                    VirtualFile baseOfSearchPath;
                    if (ref.getNode().getElementType() == PbElementTypes.IMPORT_REF) {
                        baseOfSearchPath = ref.getContainingFile().getVirtualFile().getParent();
                    } else {
                        baseOfSearchPath = ref.getProject().getBaseDir();
                    }

                    VirtualFile vfile = baseOfSearchPath.findFileByRelativePath(relativePath);
                    if (vfile != null) {
                        return ref.getManager().findFile(vfile);
                    } else {
                        return null;
                    }
                }
                case PACKAGE: {
                    String qualifiedName = PbPsiUtil.getQualifiedReferenceText(ref);
                    JavaPsiFacade facade = JavaPsiFacade.getInstance(ref.getManager().getProject());
                    return facade.findPackage(qualifiedName);
                }
                case MESSAGE_OR_GROUP:
                case MESSAGE_OR_ENUM_OR_GROUP:
                case MESSAGE_OR_PACKAGE_OR_GROUP:
                case EXTEND_FIELD: {
                    if (qualifier != null) {    //foo.bar
                        final PsiElement resolvedElement = qualifier.resolve();
                        if (resolvedElement != null) {
                            return PbResolveUtil.resolveInScope(PbPsiUtil.getScope(resolvedElement), ref);
                        }
                    } else if (ref.findChildByType(DOT) != null) {  //.foo
                        return PbResolveUtil.resolveInScope(PbPsiUtil.getRootScope(ref), ref);
                    } else {    // foo
                        PsiElement upperScope = PbPsiUtil.getUpperScope(ref);
                        while (upperScope != null) {
                            PsiElement resolveResult = PbResolveUtil.resolveInScope(upperScope, ref);
                            if (resolveResult != null) {
                                return resolveResult;
                            }
                            upperScope = PbPsiUtil.getUpperScope(upperScope);
                        }
                    }
                }
                break;
                case MESSAGE_OR_GROUP_FIELD: {
                    if (qualifier != null) {
                        final PsiElement resolvedElement = qualifier.resolve();
                        if (resolvedElement != null) {
                            return PbResolveUtil.resolveInScope(PbPsiUtil.getTypeScope(resolvedElement), ref);
                        }
                    }
                }
                break;
            }
            return null;
        }
    }

}
