package protobuf.lang.parser.declaration;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import protobuf.lang.psi.api.member.PbOptionAssignment;
import protobuf.util.PbTestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A test case to verify option assignments.
 * @author Travis Cripps
 */
public class PbParserOptionAssignmentTestCase extends LightCodeInsightFixtureTestCase {
    
    private Logger LOG = Logger.getInstance(PbParserOptionAssignmentTestCase.class);

    private ArrayList<PbOptionAssignment> optionAssignments;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        optionAssignments = new ArrayList<PbOptionAssignment>();
        populateOptionAssignmentsFromTestFile("option1.test");
    }

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() +"parser/declaration/";
    }

    private void populateOptionAssignmentsFromTestFile(String fileName) {
        Pair<String, String> testMaterial = null;
        final String testFilePath = getBasePath() + fileName;
        try {
            testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(testFilePath, true);
        } catch (IOException e) {
            LOG.error("Could not read test data file: " + testFilePath, e);
            fail();
        }
        
        final PsiFile psiFile = PbTestUtil.createPseudoProtoFile(getProject(), fileName, testMaterial.getFirst());
        PsiElement[] documentEls = psiFile.getChildren();
        for (PsiElement anEl : documentEls) {
            if (anEl instanceof PbOptionAssignment) {
                optionAssignments.add((PbOptionAssignment)anEl);
            }
        }
        assertEquals(getOptionAssignments().size(), 3);
    }
    
    private List<PbOptionAssignment> getOptionAssignments() {
        return optionAssignments;
    }

    public void testValidateOption1() {
        final PbOptionAssignment optionAssignment = getOptionAssignments().get(0);
        assertEquals(optionAssignment.getOptionName(), "a");
        assertEquals(optionAssignment.getOptionValue(), "1");
    }

    public void testValidateOption2() {
        final PbOptionAssignment optionAssignment = getOptionAssignments().get(1);
        assertEquals(optionAssignment.getOptionName(), "(b)");
        assertEquals(optionAssignment.getOptionValue(), "5");
    }

    public void testValidateOption3() {
        final PbOptionAssignment optionAssignment = getOptionAssignments().get(2);
        assertEquals(optionAssignment.getOptionName(), "java_package");
        assertEquals(optionAssignment.getOptionValue(), "org.example.test");
    }

}
