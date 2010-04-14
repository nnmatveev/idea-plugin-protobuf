package protobuf.lang.resolve;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import protobuf.util.PbTestUtil;

import java.io.File;
import java.io.IOException;

/**
 * author: Nikolay Matveev
 */


public class PbResolveTestCase extends LightCodeInsightFixtureTestCase {

    private final static Logger LOG = Logger.getInstance(PbResolveTestCase.class.getName());

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + "resolve/";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.setTestDataPath(getBasePath());
        final String dirName = getTestName(true).replace('$', '/');
        assertTrue(getBasePath() + dirName + " - directory not existed",new File(getBasePath() + dirName).exists());
        myFixture.copyDirectoryToProject(dirName, "");
    }

    protected void doSimpleRefAimTest(final String refFilePath, final String aimFilePath, boolean equals) {
        try {
            /*ProjectRootManager prm = ProjectRootManager.getInstance(myFixture.getProject());
            prm.getFileIndex().iterateContent(new ContentIterator() {
                @Override
                public boolean processFile(VirtualFile virtualFile) {
                    System.out.println(virtualFile.getPath());
                    return true;
                }
            });*/
            final VirtualFile refFile = myFixture.getTempDirFixture().getFile(refFilePath);
            final VirtualFile aimFile = myFixture.getTempDirFixture().getFile(aimFilePath);
            assertNotNull("ref file not found", refFile);
            assertNotNull("aim file not found", aimFile);
            int refOffset;
            int aimOffset;
            if (refFile.equals(aimFile)) {
                String fileText = StringUtil.convertLineSeparators(VfsUtil.loadText(refFile));
                refOffset = fileText.indexOf(PbTestUtil.REF_MARKER);
                aimOffset = fileText.indexOf(PbTestUtil.AIM_MARKER);
                assertTrue(refOffset > -1);
                assertTrue(aimOffset > -1);
                assertTrue(refOffset != aimOffset);
                if (refOffset < aimOffset) {
                    fileText = fileText.substring(0, refOffset) + fileText.substring(refOffset + PbTestUtil.REF_MARKER.length());
                    aimOffset = fileText.indexOf(PbTestUtil.AIM_MARKER);
                    fileText = fileText.substring(0, aimOffset) + fileText.substring(aimOffset + PbTestUtil.AIM_MARKER.length());
                } else {
                    fileText = fileText.substring(0, aimOffset) + fileText.substring(aimOffset + PbTestUtil.AIM_MARKER.length());
                    refOffset = fileText.indexOf(PbTestUtil.REF_MARKER);
                    fileText = fileText.substring(0, refOffset) + fileText.substring(refOffset + PbTestUtil.REF_MARKER.length());
                }
                VfsUtil.saveText(refFile, fileText);
            } else {
                String refFileText = StringUtil.convertLineSeparators(VfsUtil.loadText(refFile));
                String aimFileText = StringUtil.convertLineSeparators(VfsUtil.loadText(aimFile));
                refOffset = refFileText.indexOf(PbTestUtil.REF_MARKER);
                aimOffset = aimFileText.indexOf(PbTestUtil.AIM_MARKER);
                assertTrue(refOffset > -1);
                assertTrue(aimOffset > -1);
                refFileText = refFileText.substring(0, refOffset) + refFileText.substring(refOffset + PbTestUtil.REF_MARKER.length());
                aimFileText = aimFileText.substring(0, aimOffset) + aimFileText.substring(aimOffset + PbTestUtil.AIM_MARKER.length());
                VfsUtil.saveText(refFile, refFileText);
                VfsUtil.saveText(aimFile, aimFileText);
            }
            PsiManager psiManager = myFixture.getPsiManager();
            PsiFile psiRefFile = psiManager.findFile(refFile);
            PsiFile psiAimFile = psiManager.findFile(aimFile);
            assertNotNull(psiRefFile);
            assertNotNull(psiAimFile);
            PsiReference ref = psiRefFile.findReferenceAt(refOffset);
            assertNotNull(ref);
            PsiElement aim = psiAimFile.findElementAt(aimOffset).getParent();
            assertNotNull(aim);
            PsiElement resolved = ref.resolve();
            assertNotNull(resolved);
            if (equals) {
                //assertEquals(aim, resolved);
                assertTrue(aim.equals(resolved));
            } else {
                assertFalse(aim.equals(resolved));
            }
        } catch (Exception e) {
            assertTrue("exception",false);
            e.printStackTrace();
        }
    }

    protected void doSimpleRefPackageTest(final String refFilePath, final String packageName, boolean equals) {
        try {
            /*ProjectRootManager prm = ProjectRootManager.getInstance(myFixture.getProject());
            prm.getFileIndex().iterateContent(new ContentIterator() {
                @Override
                public boolean processFile(VirtualFile virtualFile) {
                    System.out.println(virtualFile.getPath());
                    return true;
                }
            });*/
            final VirtualFile refFile = myFixture.getTempDirFixture().getFile(refFilePath);
            assertNotNull("ref file not found", refFile);
            int refOffset;
            String refFileText = StringUtil.convertLineSeparators(VfsUtil.loadText(refFile));
            refOffset = refFileText.indexOf(PbTestUtil.REF_MARKER);
            assertTrue(refOffset > -1);
            refFileText = refFileText.substring(0, refOffset) + refFileText.substring(refOffset + PbTestUtil.REF_MARKER.length());
            VfsUtil.saveText(refFile, refFileText);
            PsiManager psiManager = myFixture.getPsiManager();
            PsiFile psiRefFile = psiManager.findFile(refFile);
            assertNotNull(psiRefFile);
            JavaPsiFacade psiFacade = myFixture.getJavaFacade();
            PsiPackage aimPackage =psiFacade.findPackage(packageName);
            assertNotNull(aimPackage);
            PsiReference ref = psiRefFile.findReferenceAt(refOffset);
            assertNotNull(ref);                        
            PsiElement resolved = ref.resolve();
            assertNotNull(resolved);
            if (equals) {                
                assertTrue(aimPackage.equals(resolved));
            } else {
                assertFalse(aimPackage.equals(resolved));
            }
        } catch (Exception e) {
            assertTrue("exception",false);
            e.printStackTrace();
        }
    }

    protected void doSimpleRefFileTest(final String refFilePath, final String aimFilePath, boolean equals) {
        try {
            /*ProjectRootManager prm = ProjectRootManager.getInstance(myFixture.getProject());
            prm.getFileIndex().iterateContent(new ContentIterator() {
                @Override
                public boolean processFile(VirtualFile virtualFile) {
                    System.out.println(virtualFile.getPath());
                    return true;
                }
            });*/
            final VirtualFile refFile = myFixture.getTempDirFixture().getFile(refFilePath);
            assertNotNull("ref file not found", refFile);
            int refOffset;
            String refFileText = StringUtil.convertLineSeparators(VfsUtil.loadText(refFile));
            refOffset = refFileText.indexOf(PbTestUtil.REF_MARKER);
            assertTrue(refOffset > -1);
            refFileText = refFileText.substring(0, refOffset) + refFileText.substring(refOffset + PbTestUtil.REF_MARKER.length());
            VfsUtil.saveText(refFile, refFileText);
            PsiManager psiManager = myFixture.getPsiManager();
            PsiFile psiRefFile = psiManager.findFile(refFile);
            assertNotNull(psiRefFile);
            final VirtualFile aimFile = myFixture.getTempDirFixture().getFile(aimFilePath);
            assertNotNull(aimFile);
            PsiReference ref = psiRefFile.findReferenceAt(refOffset);
            assertNotNull(ref);
            PsiElement resolved = ref.resolve();
            assertNotNull(resolved);
            assertTrue(resolved instanceof PsiFile);
            if (equals) {
                assertTrue(aimFile.equals(((PsiFile)resolved).getVirtualFile()));
            } else {
                assertFalse(aimFile.equals(((PsiFile)resolved).getVirtualFile()));
            }
        } catch (Exception e) {
            assertTrue("exception",false);
            e.printStackTrace();
        }
    }


    //custom type

    public void testCustomType1$field$enum1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomType1$field$group1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomType1$field$message1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomType1$servicemethod$group1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomType1$servicemethod$message1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomType1$extend$group1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomType1$extend$message1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    //custom option

    public void testCustomOption1$extendfield1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomOption1$messageorgroupfield1() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomOption1$messageorgroupfield2() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomOption1$messageorgroupfield3() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomOption1$messageorgroupfield4() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    public void testCustomOption1$messageorgroupfield5() {
        doSimpleRefAimTest("a.proto", "a.proto", true);
    }

    //package

    public void testPackage1$messageorpackageorgroup1() {
        doSimpleRefPackageTest("a.proto", "my", true);
    }
    public void testPackage1$package1() {
        doSimpleRefPackageTest("com/intellij/a.proto", "com.intellij", true);
    }

    //file
    public void testFile1$file1() {
        doSimpleRefFileTest("a.proto", "com/intellij/b.proto", true);
    }

}
