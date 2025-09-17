package mediator;

abstract class UIComponentBase {
    protected IUIMediator mediator;

    public UIComponentBase(IUIMediator mediator) {
        this.mediator = mediator;
    }

    public void notifyMediator() {
        this.mediator.componentChanged(this);
    }
}
