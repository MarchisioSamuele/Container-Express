package knapsack_package;

import java.util.Vector;

public class GreedyKnapsack  {
    private double[] prezzoProdotti;
    private double totVolume;
    private double[] volumeProdotti;
    private double[] pesoProdotti;
    public int contContainers = 0;
    private double totWeight;
    private Vector<Container> containers;


    public GreedyKnapsack() {

        prezzoProdotti = new double[]{2, 3, 5, 7, 1, 3, 10};
        volumeProdotti = new double[]{2, 5, 7, 3, 4, 2, 1};
        pesoProdotti = new double[]{4, 6, 9, 1, 2, 7, 10};
        containers = new Vector<>();
        for(int i = 0;i<volumeProdotti.length;i++){
            totVolume=totVolume+volumeProdotti[i];
        }
        for(int i = 0;i<pesoProdotti.length;i++){
            totWeight=totWeight+pesoProdotti[i];
        }
    }
    public void scambia(int sx,int dx){
        double temp = prezzoProdotti[sx];
        prezzoProdotti[sx] = prezzoProdotti[dx];
        prezzoProdotti[dx] = temp;

        double temp1 = volumeProdotti[sx];
        volumeProdotti[sx] = volumeProdotti[dx];
        volumeProdotti[dx] = temp1;
    }
    private void unitPriceOrder(int z) {
        boolean esci = false;
        while( z < prezzoProdotti.length && esci==false){
            int j = z+1;
            esci = true;
            while (j < prezzoProdotti.length ) {
                double x=prezzoProdotti[j - 1] / volumeProdotti[j - 1];
                double y=prezzoProdotti[j] / volumeProdotti[j];
                if (x <=y) {
                    esci = false;
                    double temp = prezzoProdotti[j - 1];
                    prezzoProdotti[j - 1] = prezzoProdotti[j];
                    prezzoProdotti[j] = temp;

                    double temp1 = volumeProdotti[j - 1];
                    volumeProdotti[j - 1] = volumeProdotti[j];
                    volumeProdotti[j] = temp1;
                }j++;
            }z++;
        }
    }
    private void unitWeightOrder(int z) { //ordina il volumeProdotti in base al maggiore
        int i=z;
        boolean esci = false;
        while(i < volumeProdotti.length && esci ==false)  {
            int j =z+1;
            esci = true;
            while(j < volumeProdotti.length - 1 ) {
                double x= volumeProdotti[j - 1];
                double y= volumeProdotti[j];
                if (x <y) {
                    esci = false;
                    double temp = prezzoProdotti[j - 1];
                    prezzoProdotti[j - 1] = prezzoProdotti[j];
                    prezzoProdotti[j] = temp;
                    double temp1 = volumeProdotti[j - 1];
                    volumeProdotti[j - 1] = volumeProdotti[j];
                    volumeProdotti[j] = temp1;
                    double temp2 = pesoProdotti[j-1];
                    pesoProdotti[j-1] = pesoProdotti[j];
                    pesoProdotti[j] = temp2;

                }j++;
            }i++;
        }
    }

    public void knapsack(Container container,int j) { //riempe il vettore con 1.00 se riesce a metterlo altrimenti la parte che riesce a mettere
        if (j == 0) {
            unitWeightOrder(j);
            containers.add(chooseMinContainerVolume(totVolume));
            if(containers.get(0)==null) {
                containers.set(0,chooseMaxContainerVolume());
            }
        }
        boolean esci = false;
        while(j < volumeProdotti.length && esci==false)  {
            if ((volumeProdotti[j] <=containers.get(contContainers).getVolume()  ) & (pesoProdotti[j] <= containers.get(contContainers).getWeight())) {
                containers.get(contContainers).incVolume(-volumeProdotti[j]);
                totVolume -= volumeProdotti[j];
                containers.get(contContainers).incWeight(-pesoProdotti[j]);
                totWeight -=pesoProdotti[j];
                j++;
                unitWeightOrder(j);
            } else {
                esci = true;
            }
        }if (j < volumeProdotti.length) {
            int retIndex = -1;
            if( containers.get(contContainers).getVolume() == 0){
                retIndex = j;
                containers.add(chooseMinContainerVolume(totVolume));
                if(containers.get(j)==null) {
                    containers.set(j,chooseMaxContainerVolume());
                }
                contContainers++;
            }
            while(retIndex==-1){
                unitWeightOrder(j);
                retIndex =  RicercaDicotomica.cerca(volumeProdotti.length, containers.get(contContainers).getVolume(), volumeProdotti,j);
                if(retIndex==-1) {

                }
            }
            scambia(j,retIndex);
            knapsack(container,j);
        }
    }

    public void print() {

        System.out.println("indice" + "|" + "prezzo" + "|" + "volume" +
                "|" + "unità prezzo");
        for (int n = 0; n < prezzoProdotti.length; n++) {
            System.out.println(n + "\t" + prezzoProdotti[n] + "\t" + volumeProdotti[n] + "\t"
                    + (prezzoProdotti[n] / volumeProdotti[n]));
        }
    }

    private Container chooseMinContainerVolume(double volume){
        Container container = null;
        if(Values.volumeA < Values.volumeB && Values.volumeC > Values.volumeA){
            if(Values.volumeA>= volume){
                container = new ContainerA();
            }else if(Values.volumeC>Values.volumeB){
                if(Values.volumeB>=volume) {
                    container = new ContainerB();
                }
            }else{
                container = new ContainerC();
            }
        }
        if(Values.volumeB < Values.volumeA && Values.volumeC > Values.volumeB){
            if(Values.volumeB>= volume){
                container = new ContainerB();
            }else if(Values.volumeC>Values.volumeA){
                if(Values.volumeA>=volume) {
                    container = new ContainerA();
                }
            }else{
                container = new ContainerC();
            }
        }
        if(Values.volumeC < Values.volumeB && Values.volumeA > Values.volumeC){
            if(Values.volumeC >= volume){
                container = new ContainerC();
            }else if(Values.volumeB<Values.volumeA){
                if(Values.volumeB>=volume) {
                    container = new ContainerB();
                }
            }else{
                container = new ContainerA();
            }
        }
        return container;
    }
   private Container chooseMaxContainerVolume(){
       Container container = null;
       if(Values.volumeA>Values.volumeB && Values.volumeA>Values.volumeC){
           container = new ContainerA();
       }else if(Values.volumeB>Values.volumeC){
           container = new ContainerB();
       }else{
           container = new ContainerC();
       }
       return container;
   }

    public static void main(String args[]) {
        GreedyKnapsack g = new GreedyKnapsack();
        Container container = new Container(0,0,0,10);
        g.knapsack(container,0);
        g.print();
        System.out.println("hai usato : "
                + ContainerA.nContainerA +"-" + ContainerB.nContainerB +"-" + ContainerC.nContainerC);
    }
}
