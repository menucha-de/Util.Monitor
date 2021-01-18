package havis.util.monitor.osgi;

import havis.util.monitor.Broker;
import havis.util.monitor.Monitor;
import havis.util.monitor.MonitorBroker;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	private final static Logger log = Logger.getLogger(Activator.class.getName());
	private ServiceTracker<Monitor, Monitor> monitorTracker;
	private ServiceRegistration<Broker> brokerService;

	@Override
	public void start(BundleContext context) throws Exception {
		final MonitorBroker broker = new MonitorBroker();

		// create and open monitor tracker
		this.monitorTracker = new ServiceTracker<Monitor, Monitor>(context, Monitor.class, null) {
			@Override
			public Monitor addingService(ServiceReference<Monitor> reference) {
				Monitor service = super.addingService(reference);
				log.log(Level.FINE, "Adding monitor {0}.", service.getClass().getName());
				broker.add(service);
				return service;
			}

			@Override
			public void removedService(ServiceReference<Monitor> reference, Monitor service) {
				log.log(Level.FINE, "Removing monitor {0}.", service.getClass().getName());
				broker.remove(service);
				super.removedService(reference, service);
			}
		};

		log.log(Level.FINE, "Opening tracker {0}.", monitorTracker.getClass().getName());
		this.monitorTracker.open();
		this.brokerService = context.registerService(Broker.class, broker, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (this.brokerService != null)
			this.brokerService.unregister();

		log.log(Level.FINE, "Closing tracker {0}.", monitorTracker.getClass().getName());
		if (this.monitorTracker != null)
			this.monitorTracker.close();
	}
}
