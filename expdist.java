import java.util.Random;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wei Wang
 */
public class expdist {
    double gen(int k,double num){
        double sum=0;
        for (int i=0;i<k;i++){

            sum+=Math.log(1-Math.random())/(-num);

        }
        return sum;
        
    }
    
}
