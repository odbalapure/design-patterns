package decorator;

public class TextRenderAppDemo {
    public static void main(String[] args) {
        ITextView text = new PlainTextView("Hello, World!");

        System.out.println("Plain:");
        text.render();
        System.out.println("\n");

        System.out.println("Bold:");
        ITextView boldText = new BoldDecorator(text);
        boldText.render();
        System.out.println("\n");

        System.out.println("Italic + Underline:");
        ITextView italicUnderline = new UnderlineDecorator(new BoldDecorator(text));
        italicUnderline.render();
        System.out.println("\n");

        System.out.println("Bold + Italic + Underline:");
        ITextView allStyles = new BoldDecorator(new ItalicDecorator(new UnderlineDecorator(text)));
        allStyles.render();
        System.out.println("\n");

        // Plain:
        // Hello, World!

        // Bold:
        // <b>Hello, World!</b>

        // Italic + Underline:
        // <u><b>Hello, World!</b></u>

        // Bold + Italic + Underline:
        // <b><i><u>Hello, World!</u></i></b>
    }
}
