
package com.golthiware.g13.controller;

/**
 *
 */
public class G13ToBeanAdaptor {

    private final G13 g13;
    private final G13Bean bean;

    public G13ToBeanAdaptor(G13 g13) {
        this.g13 = g13;
        this.bean = new G13Bean();
    }

    public G13Bean getBean() {
        return bean;
    }
    
    public void refresh() throws InterruptedException {
        G13State state = g13.read();
        bean.setState(state);
    }
    
}
