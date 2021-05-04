package com.destroytheai.Entidades;

public class Habitaciones {
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean spawner;
    private boolean fin;
    private boolean jefe;
    private int numEn;

    public Habitaciones(int x, int y, int w, int h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }

    public boolean colisionan(Habitaciones h1, Habitaciones h2){
        if((h2.getX()>=h1.getX() && h2.getX()<=h1.getX()+h1.getW()) && (h2.getY()>=h1.getY() && h2.getY()<h1.getY()+h1.getH())){
            return true;
        } else {
            return false;
        }
    }

    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public boolean isSpawner() {
        return spawner;
    }
    public void setSpawner(boolean spawner) {
        this.spawner = spawner;
    }
    public boolean isFin() {
        return fin;
    }
    public void setFin(boolean fin) {
        this.fin = fin;
    }
    public int getNumEn() {
        return numEn;
    }
    public void setNumEn(int numEn) {
        this.numEn = numEn;
    }
    public boolean isJefe() {
        return jefe;
    }
    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }
}
