package havis.util.monitor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Broker for all monitors
 */
public class MonitorBroker implements Broker {

	private List<Monitor> monitors = new CopyOnWriteArrayList<>();

	/**
	 * Add a monitor
	 * 
	 * @param monitor
	 *            the monitor to add
	 */
	public void add(Monitor monitor) {
		this.monitors.add(monitor);
	}

	/**
	 * Remove a monitor
	 * 
	 * @param monitor
	 *            the monitor to remove
	 */
	public void remove(Monitor monitor) {
		this.monitors.remove(monitor);
	}

	/**
	 * Notify all monitors
	 * 
	 * @param source
	 *            the source of the event
	 * @param event
	 *            the event
	 */
	@Override
	public void notify(Source source, Event event) {
		for (Monitor monitor : this.monitors) {
			monitor.notify(source, event);
		}
	}
}
