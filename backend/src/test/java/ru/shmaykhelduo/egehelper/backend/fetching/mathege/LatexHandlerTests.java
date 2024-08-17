package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LatexHandlerTests {
    @Test
    public void testExtract() {
        String in = """
                \\documentclass[10pt, a4paper]{article}
                \\usepackage[final]{graphicx}
                \\pagestyle{empty}
                \\usepackage[utf8]{inputenc}
                \\usepackage[russian]{babel}
                \\usepackage{amssymb}
                \\usepackage{amsmath}
                \\usepackage{tabularx}
                \\usepackage{longtable}
                \\usepackage{wrapfig}
                \\begin{document}
                \\begin{wrapfigure}{r}{0cm}{\\includegraphics{2c05ba52608ef1886c040f2179bdb670.eps}}\\end{wrapfigure}
                \\noindent
                В треугольнике $ABC$ угол $C$ равен $90^\\circ$, $AB=5$, $\\sin A=\\dfrac{7}{25}$. Найдите $AC$.
                \\end{document}""";

        String expectedOut = "В треугольнике $ABC$ угол $C$ равен $90^\\circ$, $AB=5$, $\\sin A=\\dfrac{7}{25}$. Найдите $AC$.";
        String out = LatexHandler.extract(in);
        Assertions.assertEquals(expectedOut, out);
    }
}
