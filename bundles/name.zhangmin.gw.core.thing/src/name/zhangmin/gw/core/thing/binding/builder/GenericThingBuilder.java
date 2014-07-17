package name.zhangmin.gw.core.thing.binding.builder;



import java.util.List;

import name.zhangmin.gw.config.Configuration;
import name.zhangmin.gw.core.thing.Channel;
import name.zhangmin.gw.core.thing.Thing;
import name.zhangmin.gw.core.thing.impl.ThingImpl;

import com.google.common.collect.Lists;

public class GenericThingBuilder<T extends GenericThingBuilder<T>> {

	private ThingImpl thing;
	
	protected GenericThingBuilder(ThingImpl thing) {
		this.thing = thing;
	}
	public T withChannels(Channel... channels) {
		this.thing.setChannels(Lists.newArrayList(channels));
		return self();
	}
    public T withChannels(List<Channel> channels) {
        this.thing.setChannels(channels);
        return self();
    }
    
	public T withConfiguration(Configuration configuration) {
		this.thing.setConfiguration(configuration);
		return self();
	}
    public Thing build() {
	    return this.thing;
	}

    @SuppressWarnings("unchecked")
	protected T self() {
	    return (T) this;
	}
}
