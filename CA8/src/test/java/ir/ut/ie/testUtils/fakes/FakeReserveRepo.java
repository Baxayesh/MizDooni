package ir.ut.ie.testUtils.fakes;

import ir.ut.ie.database.IReserveRepository;
import ir.ut.ie.exceptions.NotExistentReserve;
import ir.ut.ie.models.Reserve;

import java.util.ArrayList;
import java.util.List;


public class FakeReserveRepo implements IReserveRepository {


    final List<Reserve> memory;

    public FakeReserveRepo(){
        memory = new ArrayList<>();
    }


    @Override
    public int add(Reserve reserve) {
        memory.add(reserve);
        reserve.setReserveNumber(memory.size());
        return reserve.getReserveNumber();
    }

    @Override
    public boolean doUserHasAnyPassedReserveAt(String user, String restaurant) {
        for (var reserve : memory){
            if(
                reserve.getReservee().is(user) &&
                reserve.getRestaurant().is(restaurant) &&
                reserve.IsActive() && reserve.IsPassed()
            ){
                return true;
            }
        }

        return false;
    }

    @Override
    public Reserve get(String reservee, int reserveNumber) throws NotExistentReserve {
        for (var reserve : memory){
            if(
                reserve.getReservee().is(reservee) &&
                reserve.getReserveNumber() == reserveNumber
            ){
                return reserve;
            }
        }
        throw new NotExistentReserve();
    }

    @Override
    public Reserve[] get(String reservee) {

        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void update(Reserve reserve) {
        throw new RuntimeException("Not Implemented");
    }
}
