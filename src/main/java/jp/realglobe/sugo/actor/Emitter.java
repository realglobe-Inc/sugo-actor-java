/*----------------------------------------------------------------------
 * Copyright 2017 realglobe Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *----------------------------------------------------------------------*/

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
