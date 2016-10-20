package knapsack_package;

/**
 * Created by Luca on 14/10/2016.
 */
public class RicercaDicotomica {
    public static int cerca(int n, double ele, double vet[],int sx)
    {
        int sx1=sx;//estremo sinistro
        int dx=n-1;//estremo destro
        do
        {
            int md=(dx+sx)/2;//valore medio
            if(ele>=vet[md])
                return md;//elemento trovato nell indice md
            else
            if(ele>vet[md])
                dx=md-1;
            else
                sx=md+1;
        }
        while(sx<=dx);
        return -1;
    }
}
