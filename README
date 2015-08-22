PLUGIN AUTHOR
Matveev Nikolay

CONTRIBUTOR
Travis Cripps

USEFUL LINKS:
1. Protocol Buffers brief and only documentation -
   https://developers.google.com/protocol-buffers/

CONFIGURE DEVELOPMENT
1. Set 'IDEA sdk' as the name of the project IDEA plugin SDK
2. Project Java language level is 1.6

HOW TO compile plugin:
1. Just add idea.jar to your IDEA SDK classpath(justification of it you can find in AUTHOR NOTE#4)

AUTHOR NOTES:
1. Protocol Buffers language haven't any reserved keywords, thus I have to be tricky in some places.
2. [protobuf.lang.psi.impl.reference.PbRefImpl:355:21] Using LightCodeFixtureTextCase leads to problems with temp
   directory, thus I have to use such hack to make method works both in real and test mode.
3. Plugin is idea.jar-dependent, because it uses very useful implementation of PsiElement - ASTWrapperPsiElement,
   testing of plugin bases on class LightCodeInsightFixtureTestCase extending UsefulTestCase in idea.jar and several not
   so important, but time-saving use cases.

KNOWN ISSUES:
1. Problems with fixing highlighting:
    1. While annotating I can change a color of the bold text, but cannot apply Font.PLAIN attribute(but Font.ITALIC
       works)
    2. I try to fix a lot of highlighting misses, but in some cases it is still incorrect. For example if anybody
       creates enum with enum constant named "true" and then specified it as a option value. I believe that it is
       exotic.


2. Compiler:
    I don't know how compiler will behave if the "output source directory" located separate from project

3.  a lot of to do in the code...

CONTRIBUTION AND PLUGIN WRITING STUDY
All sources are available on http://github.com/nnmatveev/idea-plugin-protobuf
License is absent.
To contact author use the github mailbox.

