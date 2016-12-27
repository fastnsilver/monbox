package com.fns.monbox.collector;

import java.util.List;
import java.util.Map;

import com.fns.monbox.model.CloudInstance;
import com.fns.monbox.model.CloudSubNetwork;
import com.fns.monbox.model.CloudVirtualNetwork;
import com.fns.monbox.model.CloudVolumeStorage;
import com.fns.monbox.repository.CloudInstanceRepository;
import com.fns.monbox.repository.CloudSubNetworkRepository;
import com.fns.monbox.repository.CloudVirtualNetworkRepository;


public interface AWSCloudClient {

	Map<String, List<CloudInstance>> getCloundInstances(CloudInstanceRepository repository);
    
    CloudVirtualNetwork getCloudVPC(CloudVirtualNetworkRepository repository);
    CloudSubNetwork getCloudSubnet(CloudSubNetworkRepository repository);
    
    Map<String, List<CloudVolumeStorage>> getCloudVolumes(Map<String, String> instanceToAccountMap);
    
    Double get24HourInstanceEstimatedCharge();

    /* Averages CPUUtil every minute for the last hour */
    Double getInstanceCPUSinceLastRun(String instanceId, long lastUpdated);

    /* Averages CPUUtil every minute for the last hour */
    Double getLastHourInstanceNetworkIn(String instanceId,
                                        long lastUpdated);

    /* Averages CPUUtil every minute for the last hour */
    Double getLastHourIntanceNetworkOut(String instanceId, long lastUpdated);

    /* Averages CPUUtil every minute for the last hour */
    Double getLastHourInstanceDiskRead(String instanceId,
                                       long lastUpdated);

    Double getLastInstanceHourDiskWrite(String instanceId);
}
