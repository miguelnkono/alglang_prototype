
package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class AstGenerator
{
    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.err.println("Usage: AstGenerator <filename>");
            System.exit(64);
        }

        String __output_directory__ = args[0];

        defineAst(__output_directory__, "Expr", Arrays.asList(
                "Binary : Expr __left__, Token __operator__, Expr __right__",
                "Grouping : Expr __expression__",
                "Unary : Token __operator__, Expr __right__",
                "Literal : Valuable __value__"
        ), false);

        defineAst(__output_directory__, "ExprFinal", Arrays.asList(
                "Binary : Expr __left__, Token __operator__, Expr __right__",
                "Grouping : Expr __expression__",
                "Unary : Token __operator__, Expr __right__",
                "Literal : Valuable __value__"
        ), true);
//        defineAst(__output_directory__, "Stmt", Arrays.asList(
//                "Expression: Expr expression",
//                "Print: Expr expression",
//                "Var:   Token name, Expr initializer"
//        ), false);
    }

    /**
     * Create a new file in the output directory with a given name and containing all the
     * subclasses that will construct the ast tree.
     *
     * @param __output_directory__ the name of the directory where the "name" tree will be created.
     * @param __name__            represent the name of the parent class for all the subclasses for the tree.
     * @param types           the description of the of each types present in the tree.
     *
     */
    private static void defineAst(String __output_directory__, String __name__, List<String> types,
                                  boolean __is_typed__) throws IOException
    {
        String filePath = __output_directory__ + "/" + __name__ + ".java";

        try (PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8))
        {
            writer.println("package org.example;\n");
            writer.println("public abstract class " + __name__ + "\n{");

            // define the visitor pattern interface.
            defineVisitor(writer, __name__, types);

            // we define each subclasses
            for (String type : types)
            {
                // get the name, the fields of the current and actually define it.
                String subclassName = type.split(":")[0].trim();
                String fields = type.split(":")[1].trim();
                defineType(writer, __name__, subclassName, fields);
            }

            if (__is_typed__) {
                writer.printf("\tprivate Typable __type__;\n\n");
                writer.printf("\tpublic Typable getType() { return __type__; }\n");
                writer.print("\tpublic void setType(Typable __type__) { this.__type__ = __type__; }\n\n");
            }

            // define the accept method
            writer.printf("\tpublic abstract <R> R accept(Visitor<R> visitor);\n");

            writer.println("}");
        } catch (FileNotFoundException e)
        {
            System.err.println("File not found: " + filePath);
        }
    }

    /**
     * This function define the visitor interface for our interpreter.
     *
     * @param writer the object that we use to write into the base name class
     * @param name   which is the name of the base class
     * @param types  which is the types support by the visitor pattern interface and
     *               implementation.
     **/
    private static void defineVisitor(PrintWriter writer, String name, List<String> types)
    {
        writer.printf("\tpublic interface Visitor<R> {\n");

        for (String type : types)
        {
            String visitorMethodName = type.split(":")[0].trim();
            writer.printf("\t\t R visit%s%s (%s __%s__);\n", visitorMethodName, name,
                    visitorMethodName, name.toLowerCase());
        }

        writer.printf("\t}\n\n");
    }

    /**
     * This is the function that will actually define the subclasses of the base class with all
     * of its fields.
     *
     * @param writer       the writer pointer that will allow us to write into the baseName file.
     * @param baseName     the base name on which all the subclasses will inherit from.
     * @param subclassName the name of the base class that will inherit the base class.
     * @param fieldList    the fields of the subclass.
     *
     */
    private static void defineType(PrintWriter writer, String baseName, String subclassName,
                                   String fieldList)
    {
        // static class Binary extends Expr {}
        writer.printf("    public static class %s extends %s \n    {\n", subclassName, baseName);

        // Binary (Expr left, Token operator, Expr right){}
        writer.printf("        public %s (%s)\n        {\n", subclassName, fieldList);
        // the parameters in the constructor of the subclass.
        String[] fields = fieldList.split(", ");
        for (String field : fields)
        {
            String name = field.split(" ")[1];
            writer.printf("            this.%s = %s;\n", name, name);
        }
        writer.println("        }\n");

        // implement the accept method of the visitor pattern interface.
        writer.printf("\t\t@Override\n");
        writer.printf("\t\tpublic <R> R accept(Visitor<R> visitor) {\n");
        writer.printf("\t\t\treturn visitor.visit%s%s(this);\n", subclassName, baseName);
        writer.printf("\t\t}\n\n");

        // fields
        for (String field : fields)
        {
            writer.printf("\t\tpublic final %s;\n", field);
        }

        writer.println("    }\n");
    }
}
