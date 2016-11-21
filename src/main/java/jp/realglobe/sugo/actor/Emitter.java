package jp.realglobe.sugo.actor;

/**
 * 送信機。
 * モジュール関数内で emit する場合に継承する
 */
public abstract class Emitter {

    private final String name;
    private Actor.ActorEmitter emitter;

    /**
     * 作成する
     * @param name モジュール名
     */
    protected Emitter(final String name) {
        this.name = name;
    }

    /**
     * 送る
     * @param event イベント
     * @param data 添付データ。JSON 化可能でなければならない
     */
    public void emit(final String event, final Object data) {
        this.emitter.emit(this.name, event, data);
    }

    /**
     * 低層の送信機を登録する。
     * Actor から呼び出す
     * @param emitter 低層の送信機
     */
    final void setEmitter(final Actor.ActorEmitter emitter) {
        this.emitter = emitter;
    }

}
