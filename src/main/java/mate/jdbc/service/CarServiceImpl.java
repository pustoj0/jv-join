package mate.jdbc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import mate.jdbc.dao.CarDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Inject;
import mate.jdbc.lib.Service;
import mate.jdbc.model.Car;
import mate.jdbc.model.Driver;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        if (carDao.get(id).isPresent()) {
            return carDao.get(id).get();
        }
        throw new DataProcessingException("Car by id: " + id + " don't exist");
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        if (car.getDrivers() == null) {
            List<Driver> drivers = new ArrayList<>();
            drivers.add(driver);
            car.setDrivers(drivers);
            update(car);
            return;
        }
        List<Driver> drivers = new ArrayList<>(car.getDrivers());
        drivers.add(driver);
        car.setDrivers(drivers);
        carDao.update(car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        List<Driver> drivers = new ArrayList<>(car.getDrivers());
        drivers.remove(driver);
        car.setDrivers(drivers);
        carDao.update(car);
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAllByDriver(driverId);
    }

}
