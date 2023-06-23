package rs.ac.bg.etf.pp1;

import java.io.*;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    public static void main(String[] args) throws Exception {

        Logger log = Logger.getLogger(Compiler.class);

        Reader br = null;
        try {
            File sourceCode = new File("test/program.mj");
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));
            Yylex lexer = new Yylex(br);

            MJParser p = new MJParser(lexer);
            Symbol s = p.parse();  //pocetak parsiranja

            Program prog = (Program) (s.value);
            Tab.init();
            // ispis sintaksnog stabla
            log.info(prog.toString(""));
            log.info("===================================");

            // ispis prepoznatih programskih konstrukcija
            SemanticAnalyzer v = new SemanticAnalyzer();
            prog.traverseBottomUp(v);

            log.info(" Pozvanih \"print\" naredbi ima = " + v.printCallCount);
            log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);

            log.info("===================================");
            Tab.dump();

            if (!p.errorDetected) {
                log.info("Parsiranje uspesno zavrseno");
                if (!v.errorDetected) {
                    log.info("Ulaz je semanticki ispravan");
                    File objFile = new File("test/program.obj");
                    if (objFile.exists())
                        objFile.delete();
                    CodeGenerator cd = new CodeGenerator();
                    prog.traverseBottomUp(cd);
                    Code.dataSize = v.varDeclCount;
                    Code.mainPc = cd.getMainPc();
                    Code.write(new FileOutputStream(objFile));
                }else{
                    log.info("Ulaz nije semanticki ispravan");
                }
            } else {
                log.info("Parsiranje nije uspesno zavrseno");
            }
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e1) {
                log.error(e1.getMessage(), e1);
            }
        }

    }


}
