import java.time.LocalTime;
import java.util.Objects;

public class Bus {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String nameOfBus;

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(final LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(final LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNameOfBus() {
        return nameOfBus;
    }

    public void setNameOfBus(final String nameOfBus) {
        this.nameOfBus = nameOfBus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bus)) {
            return false;
        }
        Bus bus = (Bus) o;
        return Objects.equals(getDepartureTime(), bus.getDepartureTime())
                && Objects.equals(getArrivalTime(), bus.getArrivalTime())
                && Objects.equals(getNameOfBus(), bus.getNameOfBus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDepartureTime(), getArrivalTime(),
                getNameOfBus());
    }

    @Override
    public String toString() {
        return this.nameOfBus + " " + this.getDepartureTime()
                + " " + this.getArrivalTime();
    }
}
