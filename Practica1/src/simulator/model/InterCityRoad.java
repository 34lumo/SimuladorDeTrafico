package simulator.model;

public class InterCityRoad extends Road {
    public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
	public void reduceTotalContamination() {
        int reductionFactor = switch (this.getWeather()) {
            case SUNNY -> 2;
            case CLOUDY -> 3;
            case RAINY -> 10;
            case WINDY -> 15;
            case STORM -> 20;
        };
        this.totalCO2 = Math.max(0, this.totalCO2 - ((100 - reductionFactor) * this.totalCO2 / 100));
    }

    @Override
	public void updateSpeedLimit() {
        if (this.totalCO2 > this.getContLimit()) {
            this.currentSpeedLimit = this.getMaxSpeed() / 2;
        } else {
            this.currentSpeedLimit = this.getMaxSpeed();
        }
    }
    
    @Override
    public int calculateVehicleSpeed(Vehicle v) {
    	int calculatedSpeed;
    	if (this.weather == Weather.STORM) {
    		calculatedSpeed = (this.currentSpeedLimit * 8) / 10;
    	} else {
    		calculatedSpeed = this.currentSpeedLimit;
    	}

    	return calculatedSpeed;
    }


}
