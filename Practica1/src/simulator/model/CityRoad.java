
package simulator.model;

public class CityRoad extends Road {
    public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
	public void reduceTotalContamination() {
        int reduction = this.weather == Weather.WINDY || this.weather == Weather.STORM ? 10 : 2;
        this.totalCO2 = Math.max(0, this.totalCO2 - reduction);
    }

    @Override
	public void updateSpeedLimit() {
        this.currentSpeedLimit = this.getMaxSpeed(); // City roads do not change speed limits based on contamination.
    }

    @Override
	public int calculateVehicleSpeed(Vehicle v) {
        return ((11 - v.getContClass()) * this.currentSpeedLimit / 11);
    }

}
