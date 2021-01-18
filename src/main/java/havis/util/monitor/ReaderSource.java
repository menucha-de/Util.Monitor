package havis.util.monitor;

import java.util.List;

public interface ReaderSource extends Source {

	List<Capabilities> getCapabilities(CapabilityType type);
	
	List<Configuration> getConfiguration(ConfigurationType type, short antennaId);
	
	void setConfiguration(List<Configuration> configuration);
	
}