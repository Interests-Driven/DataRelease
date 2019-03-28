package com.fa.cim;

import com.fa.cim.support.OmRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * description:
 * <p>OmReleaseApplication .<br/></p>
 * <p>
 * change history:
 * date             defect#             person             comments
 * ---------------------------------------------------------------------------------------------------------------------
 * 2019/3/15        ********             Yuri               create file
 *
 * @author: Yuri
 * @date: 2019/3/15 11:10
 * @copyright: 2019, FA Software (Shanghai) Co., Ltd. All Rights Reserved.
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = OmRepositoryFactoryBean.class)
public class OmReleaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmReleaseApplication.class, args);
	}
}
