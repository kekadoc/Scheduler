package common.lifecycle

class MutableLifecycle : Lifecycle() {
    public override fun setState(state: State) {
        super.setState(state)
    }
}