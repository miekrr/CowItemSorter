/**
 * Created by mike on 8/25/2015.
 */
public class CowClothes extends CowItem {

    double goldRate;
    double goldPercent;

    @Override
    public double getItemValue() {

        if(goldPercent == 0)
            return goldRate;

        if(goldRate == 0 )
            return goldPercent;

        return goldRate * goldPercent;
    }
}
