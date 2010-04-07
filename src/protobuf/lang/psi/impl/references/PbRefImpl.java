package protobuf.lang.psi.impl.references;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.ProtobufTokenTypes;

import static protobuf.lang.psi.PbPsiEnums.*;

import protobuf.lang.psi.ProtobufPsiElementVisitor;
import protobuf.lang.psi.api.PbFile;
import protobuf.lang.psi.api.definitions.*;
import protobuf.lang.psi.api.members.PbFieldType;
import protobuf.lang.psi.api.members.PbOptionRefSeq;
import protobuf.lang.psi.api.references.PbRef;
import protobuf.lang.psi.impl.PbPsiElementImpl;
import protobuf.lang.psi.utils.PbPsiUtil;
import protobuf.util.PbTextUtil;

import protobuf.lang.resolve.PbResolveUtil;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 30, 2010
 */
public class PbRefImpl extends PbPsiElementImpl implements PbRef {

    private final static Logger LOG = Logger.getInstance(PbRefImpl.class.getName());

    private static final ExperimentalResolver expResolver = new ExperimentalResolver();        

    @Override
    public String toString() {
        switch (getRefKind()) {
            case PACKAGE: {
                return "package reference";
            }
            case DIRECTORY: {
                return "directory reference";
            }
            case MESSAGE_OR_GROUP: {
                return "message or group reference";
            }
            case MESSAGE_OR_ENUM_OR_GROUP: {
                return "message or enum or group reference";
            }
            case MESSAGE_OR_PACKAGE_OR_GROUP: {
                return "message or package or group reference";
            }
            case MESSAGE_OR_GROUP_FIELD: {
                return "message field reference";
            }
            case EXTEND_FIELD: {
                return "extend field reference";
            }            
        }
        assert false;
        return null;
    }

    public PbRefImpl(ASTNode node) {
        super(node);
    }

    @Override
    public void accept(ProtobufPsiElementVisitor visitor) {
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

    @Override
    public PbRef getQualifier() {
        switch (getRefKind()) {
            case DIRECTORY: {
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

    /*
    switch (getRefKind()) {
            case DIRECTORY: {
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
     */


    @Override
    public String getReferenceName() {
        /*switch (getRefKind()) {
            case DIRECTORY:
            case PACKAGE:
            case MESSAGE_OR_GROUP:
            case MESSAGE_OR_ENUM_OR_GROUP:
            case MESSAGE_OR_PACKAGE_OR_GROUP:
            case MESSAGE_OR_GROUP_FIELD:            
            case EXTEND_FIELD: {
            }
        } */
        PsiElement psi = findChildByType(ProtobufTokenTypes.IK);
        return psi != null ? psi.getText() : null;
    }

    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        switch (getRefKind()) {
            case DIRECTORY: {
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
        LOG.info("getVariants invoked");
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return getManager().areElementsEquivalent(psiElement, resolve());
    }

    @Override
    public PsiElement resolve() {
        return getManager().getResolveCache().resolveWithCaching(this, expResolver, true, false);        
    }

    @Override
    public String getCanonicalText() {
        switch (getRefKind()) {
            case DIRECTORY: {
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
        if (getRefKind().equals(ReferenceKind.DIRECTORY)) {
            return PbTextUtil.trim(getText(), '"');
        }
        return null;
    }

    @Override
    public boolean isLeafReference() {
        return !(getParent() instanceof PbRef);
    }

    public ReferenceKind getRefKind() {
        //todo caching kind
        PsiElement parent = getParent();
        if (parent instanceof PbRef) {
            ReferenceKind parentKind = ((PbRefImpl) parent).getRefKind();
            assert parentKind != null;
            switch (parentKind) {
                case DIRECTORY: {
                    return ReferenceKind.DIRECTORY;
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
                    if (findChildByType(CLOSE_PARANT) != null) {
                        return ReferenceKind.EXTEND_FIELD;
                    }
                    return ReferenceKind.MESSAGE_OR_GROUP_FIELD;
                }
                default:{
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
            if (findChildByType(CLOSE_PARANT) != null) {
                return ReferenceKind.EXTEND_FIELD;
            }
            return ReferenceKind.MESSAGE_OR_GROUP_FIELD;            
        }
        if (parent instanceof PbPackageDef) {
            return ReferenceKind.PACKAGE;
        }
        if (parent instanceof PbImportDef) {
            return ReferenceKind.DIRECTORY;
        }
        //todo 'enum constants as value of options and fields', imports

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

        public PsiElement resolve(PsiReference refElement, boolean b) {
            final PbRefImpl ref = (PbRefImpl) refElement;
            final ReferenceKind refKind = ref.getRefKind();
            final PbRef qualifier = ref.getQualifier();
            switch (refKind) {
                case DIRECTORY: {
                    //todo check for wrong string literal
                    LOG.info("directory: " + PbTextUtil.trim(ref.getText(), '"'));
                    VirtualFile vfile = ref.getProject().getBaseDir().findFileByRelativePath(PbTextUtil.trim(ref.getText(), '"'));
                    if (vfile != null) {
                        PsiFile pfile = ref.getManager().findFile(vfile);
                        return pfile;
                    }
                    return null;
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
                            return PbResolveUtil.resolveInScope(PbPsiUtil.getScope(resolvedElement),ref);
                        }
                    } else if (ref.findChildByType(DOT) != null) {  //.foo
                        return PbResolveUtil.resolveInScope(PbPsiUtil.getRootScope(ref),ref);
                    } else {    // foo
                        PsiElement upperScope = PbPsiUtil.getUpperScope(ref);
                        while(upperScope != null){
                            PsiElement resolveResult = PbResolveUtil.resolveInScope(upperScope,ref);
                            if(resolveResult != null){
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
                            return PbResolveUtil.resolveInScope(PbPsiUtil.getTypeScope(resolvedElement),ref);
                        }
                    }
                }
                break;                                
            }
            return null;
        }
    }
}
