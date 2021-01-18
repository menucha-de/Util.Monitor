package havis.util.monitor;

public interface Monitor {

	void notify(Source source, Event event);
}