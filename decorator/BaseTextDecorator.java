package decorator;

public abstract class BaseTextDecorator implements ITextView {
    protected final ITextView inner;

    public BaseTextDecorator(ITextView inner) {
        this.inner = inner;
    }
}
