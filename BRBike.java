import java.io.*;
/**
 * all rights reserved by the author
 * @author Wei Wang
 */
public class BRBike {
    

    public static void main(String[] args) {
        
        //use to record 50 runs
        int[] tNCS=new int[50];
        double[] tFBA=new double[50];
        double[] tFDA=new double[50];
        double[] tTIS=new double[50];
        double[] tWB=new double[50];
        double[] tWD=new double[50];

        
        
        int counter=0;
        
        while (counter<50){
        
        
        //initialization
        //using bplustree as data structure
        //Use a sorted queue structure here
        //480 mins if go from 9am to 5pm
        //insert Ending time into the list
        Sorted_Queue Eventlist=new Sorted_Queue(480.00,'E',0);
        //insert available bike number into the array
        int[] Bikeno={7,18,15};
        //create bike waiting list
        int[] BWL={0,0,0};
        //create dock wairing list
        int[] DWL={0,0,0};
        double a=7;
        double b=6;
        double c=8;
        //create numda based on meantime for reference
        double[] numda={1/c,1/a,1/b};

        //set up probability map for the station probability
        double k=(double) 0.4;
        double[][] probabilitymap={{0,k,1},{k,0,1},{1,k,0}};
        //random function 
        //set up the maximum bike capacity
        int maxcap=20;
        //set up the current event pointer
        char currevent;
        //set it as initialization
        currevent='I';
        //set up current time
        double currtime=0;
        //set up current destination as well
        int currdest=0;
        //counters;
        int NAR=0;
        int NIS=0;
        int NCS=0;
        double IntNIS=0;
        double IntNB=0;
        double IntND=0;
        double IntNWB=0;
        double IntNWD=0;
        double dt=0;
        
        
        
        
        
        //make a while loop for event.
        while (currevent!='E'){
            int ONIS=NIS;
            int ONBtotal=0;
            int ONWBtotal=0;
            int ONWDtotal=0;
            for (int i=0;i<3;i++){
                ONBtotal+=Bikeno[i];
                ONWBtotal+=BWL[i];
                ONWDtotal+=DWL[i];
            }
            
            
            
            
            //if it's initialization
            if (currevent=='I'){
                for (int i=0;i<3;i++){
                    //create exp function object
                    expdist ra=new expdist();
                    //generate random exp time

                    double rand=ra.gen(1,numda[i]);
                    //insert arrival event

                    Eventlist=Eventlist.insert(rand,'A',i);
                }
            }

            //if it's a departure event
            if (currevent=='D'){        
                //grab destination
                
                 if (BWL[currdest]>0){
                     BWL[currdest]--;
                     //give him the bike
                     //put on a special arrival event
                     currevent='A';
                     Bikeno[currdest]--;
                 }                   
                
                //check docks availability
                //if docks are available
                if (Bikeno[currdest]<maxcap){
                    Bikeno[currdest]++;
                    //if someone is waiting for a while
                    NIS--;
                    
                    
                }else{
                    DWL[currdest]++;
                }
            }
            
            
            //if it's a arrival event
            if (currevent=='A'){
                
                
                NAR++;
                NIS++;
                if (DWL[currdest]>0){
                    //if someone is waiting to dock
                         DWL[currdest]--;
                         //docked
                         Bikeno[currdest]++;
                         NIS--;
                }
                if (Bikeno[currdest]==0){
                    BWL[currdest]++;
                }else{
                Bikeno[currdest]--;
                //if bikes are available
                //calculate how much time needed
                double timecost=0;
                expdist ra=new expdist();
                //generate erlang time
                timecost=ra.gen(5,0.75);
                //generate destination
                double rand2=(double) Math.random();
                int dest=0;
                double ceil=0;
                //create random dest based on map
                for (int j=0;j<3;j++){
                    if ((rand2<probabilitymap[currdest][j])&&(ceil<probabilitymap[currdest][j])){
                        ceil=probabilitymap[currdest][j];
                        dest=j;
                    }
                }
                
                Eventlist=Eventlist.insert(currtime+timecost, 'D',dest);

                //generate next arrival event as well
                //generate random exp time
                double rand=ra.gen(1,numda[currdest]);
                //insert arrival event
                Eventlist=Eventlist.insert(rand+currtime,'A',currdest);


                
                
                }
                
            
            }
            


            //update event
            NCS=NAR-NIS;
            dt=Eventlist.key-currtime;
            currevent=Eventlist.status;
            currtime=Eventlist.key;
            currdest=Eventlist.dest;
            
            Eventlist=Eventlist.pop();
            
            //update counter
            IntNIS+=(ONIS)*dt;
            IntNB+=(ONBtotal)*dt;
            IntNWB+=(ONWBtotal)*dt;
            IntNWD+=(ONWDtotal)*dt;            
        }
        
        IntND=20*3*currtime-IntNB;
        double FBA=IntNB/(20*3*currtime);

        double FDA=IntND/(20*3*currtime);

        double TIS=IntNIS/NAR;

        double WB=IntNWB/NAR;

        double WD=IntNWD/NAR;

        
        tNCS[counter]=NCS;
        tFBA[counter]=FBA;
        tFDA[counter]=FDA;
        tTIS[counter]=TIS;
        tWB[counter]=WB;
        tWD[counter]=WD;
        
        System.out.println(tNCS[counter]+"  "+tFBA[counter]+"  "+tFDA[counter]+"  "+tTIS[counter]+"  "+tWB[counter]+"  "+tWD[counter]);
        
        
        counter++;
        }
        
    }    
}
