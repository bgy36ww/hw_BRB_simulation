/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wei Wang
 */
public class Sorted_Queue {
    protected double key;
    protected int dest;
    protected char status;
    protected Sorted_Queue nextobj;
    public Sorted_Queue(double nkey, char nstatus){
        this.key=nkey;
        this.status=nstatus;
    }
    public Sorted_Queue(double nkey, char nstatus, int ndest){
        this.key=nkey;
        this.status=nstatus;
        this.dest=ndest;
    }
    public Sorted_Queue insert(double nkey, char nstatus, int ndest){
        Sorted_Queue nextq=new Sorted_Queue(nkey,nstatus,ndest);
        return this.recinc(nextq);
    }
    public Sorted_Queue recinc(Sorted_Queue nextq){
        if (nextq.key<this.key){
            return nextq.recinc(this);
        }else if (this.nextobj==null){
            this.nextobj=nextq;
            return this;
        }else{
            this.nextobj=nextq.recinc(this.nextobj);
            return this;
        } 
    }
    public Sorted_Queue pop(){
        return this.nextobj;
    }
    
}
