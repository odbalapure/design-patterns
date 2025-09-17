package decorator;

class BoldDecorator extends BaseTextDecorator {
    public BoldDecorator(ITextView inner) {
        super(inner);
    }

    @Override
    public void render() {
        System.out.print("<b>");
        inner.render();
        System.out.print("</b>");
    }
}

class ItalicDecorator extends BaseTextDecorator {
    public ItalicDecorator(ITextView inner) {
        super(inner);
    }

    @Override
    public void render() {
        System.out.print("<i>");
        inner.render();
        System.out.print("</i>");
    }
}

class UnderlineDecorator extends BaseTextDecorator {
    public UnderlineDecorator(ITextView inner) {
        super(inner);
    }

    @Override
    public void render() {
        System.out.print("<u>");
        inner.render();
        System.out.print("</u>");
    }
}
