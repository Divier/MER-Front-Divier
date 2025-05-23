
package com.jlcg.db.init;



public interface SyncInit {

    public abstract ResponseInit running(int i) throws InterruptedException;
}
