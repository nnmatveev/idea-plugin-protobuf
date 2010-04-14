package protobuf.findUsages;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.Query;
import protobuf.util.PbTestUtil;

import java.io.File;
import java.util.Collection;

/**
 * author: Nikolay Matveev
 */

public class PbFindUsagesTestCase extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + "findUsages/";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.setTestDataPath(getBasePath());
        final String dirName = getTestName(true).replace('$', '/');
        assertTrue(new File(getBasePath() + dirName).exists());        
        myFixture.copyDirectoryToProject(dirName, "");
    }

    public void doTest(String filePath, int expectedUsagesCount) throws Throwable {
        final VirtualFile refFile = myFixture.getTempDirFixture().getFile(filePath);
        assertNotNull("ref file not found", refFile);
        int refOffset;
        String fileText = StringUtil.convertLineSeparators(VfsUtil.loadText(refFile));
        refOffset = fileText.indexOf(PbTestUtil.REF_MARKER);
        assertTrue("REF_MARKER no found", refOffset > -1);
        fileText = fileText.substring(0, refOffset) + fileText.substring(refOffset + PbTestUtil.REF_MARKER.length());
        VfsUtil.saveText(refFile, fileText);
        PsiManager psiManager = myFixture.getPsiManager();
        PsiFile psiRefFile = psiManager.findFile(refFile);        
        assertNotNull(psiRefFile);
        PsiReference ref = psiRefFile.findReferenceAt(refOffset);
        assertNotNull("Did not find reference", ref);
        PsiElement resolved = ref.resolve();
        assertNotNull("Could not resolve reference", resolved);
        final Query<PsiReference> query;
        final GlobalSearchScope projectScope = GlobalSearchScope.projectScope(getProject());
        query = ReferencesSearch.search(resolved, projectScope);
        Collection<PsiReference> references = query.findAll();
        assertEquals(expectedUsagesCount, references.size());
    }

    public void testMessage1() throws Throwable {
        doTest("a.proto", 3);
    }

    public void testEnum1() throws Throwable {
        doTest("a.proto", 2);
    }

    public void testField1() throws Throwable {
        doTest("com/b.proto", 2);
    }

    public void testGroup1() throws Throwable {
        doTest("a.proto", 2);
    }
}
