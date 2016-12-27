package com.fns.monbox.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash("cloud_virtual_network")
public class CloudVirtualNetwork extends BaseModel{
    @Indexed
	@NotNull
    @NotBlank
    private String virtualNetworkId;
    @NotNull
    @NotBlank
    private String accountNumber;
    private String collectorItemId;
    private String cidrBlock;
    private boolean defaultNetwork;
    private String state; //pending, available etc.
    private Map<String, String> tags = new HashMap<>();
    private long creationDate;
    private long lastUpdateDate;

    public String getVirtualNetworkId() {
        return virtualNetworkId;
    }

    public void setVirtualNetworkId(String virtualNetworkId) {
        this.virtualNetworkId = virtualNetworkId;
    }

    public String getCidrBlock() {
        return cidrBlock;
    }

    public void setCidrBlock(String cidrBlock) {
        this.cidrBlock = cidrBlock;
    }

    public boolean isDefaultNetwork() {
        return defaultNetwork;
    }

    public void setDefaultNetwork(boolean defaultNetwork) {
        this.defaultNetwork = defaultNetwork;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCollectorItemId() {
        return collectorItemId;
    }

    public void setCollectorItemId(String collectorItemId) {
        this.collectorItemId = collectorItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(virtualNetworkId);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof CloudVirtualNetwork)) return false;
        CloudVirtualNetwork c =(CloudVirtualNetwork) obj;
        return Objects.equals(getVirtualNetworkId(), c.getVirtualNetworkId());
    }
}
