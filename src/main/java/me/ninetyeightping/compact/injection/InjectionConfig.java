package me.ninetyeightping.compact.injection;

import com.mongodb.client.MongoCollection;
import me.ninetyeightping.compact.Compact;
import me.ninetyeightping.compact.controller.impl.ProfileController;
import me.ninetyeightping.compact.controller.impl.RankController;
import me.ninetyeightping.compact.controller.impl.grants.impl.PunishmentController;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrant;
import me.ninetyeightping.compact.controller.impl.grants.impl.RankGrantController;
import me.ninetyeightping.compact.databasing.MongoConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"me.ninetyeightping.compact"})
public class InjectionConfig {

    public ProfileController profileController = Compact.getInstance().getProfileController();
    public RankController rankController = Compact.getInstance().getRankController();
    public RankGrantController rankGrantController = Compact.getInstance().getRankGrantController();
    public PunishmentController punishmentController = Compact.getInstance().getPunishmentController();

    @Bean
    public ProfileController profileController() {
        return profileController;
    }

    @Bean
    public RankController rankController() {
        return rankController;
    }

    @Bean
    public PunishmentController punishmentController() {
        return punishmentController;
    }

    @Bean
    public RankGrantController rankGrantController() {
        return rankGrantController;
    }
}
