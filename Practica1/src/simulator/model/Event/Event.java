package simulator.model.Event;

import simulator.model.RoadMap;

public abstract class Event implements Comparable<Event> {

    private static long _counter = 0;
    protected int _time;
    protected long _time_stamp;

    public Event(int time) {
        if (time < 1) throw new IllegalArgumentException("Tiempo invalido: " + time);
        this._time = time;
        this._time_stamp = _counter++;
    }

    public int getTime() {
        return _time;
    }

    @Override
    public int compareTo(Event o) {
        if (this._time != o._time) {
            return this._time - o._time;
        }
        return Long.compare(this._time_stamp, o._time_stamp);
    }

    abstract void execute(RoadMap map);
}