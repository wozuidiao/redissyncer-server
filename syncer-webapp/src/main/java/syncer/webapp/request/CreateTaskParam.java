// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// See the License for the specific language governing permissions and
// limitations under the License.

package syncer.webapp.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import syncer.replica.entity.FileType;
import syncer.transmission.constants.CommandKeyFilterType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanenqiang
 * @Description 描述
 * @Date 2020/12/10
 */
@Data
@Builder
public class CreateTaskParam extends BaseTaskParam implements Serializable {
    private static final long serialVersionUID = -5809782578272943998L;
    /**
     * 任务id
     */
    private String taskId;
    @ApiModelProperty(value = "pipline没次提交的最到key的数量", allowableValues = "默认值为500")
    private int batchSize;

    @Builder.Default
    private boolean sourceAcl=false;

    @Builder.Default
    private boolean targetAcl=false;

    /**
     * 源用户名
     */
    @Builder.Default
    private String sourceUserName="";
    /**
     * 目标用户名
     */
    @Builder.Default
    private String targetUserName="";

    /**
     * 抛弃Key阈值
     */
    @Builder.Default
    private long errorCount=1;

    /**
     * 迁移类型：psync/文件
     */
    @ApiModelProperty(value = "迁移类型", allowableValues = "SYNC")
    @Builder.Default
    private FileType synctype;

    /**
     * 同 synctype 旧版本为fileType
     */
    @Builder.Default
    private FileType fileType;

    @Builder.Default
    private String tasktype="total";

    @Builder.Default
    private String offsetPlace="endbuffer";


    @ApiModelProperty(value = "redis db映射关系", allowableValues = "当由此描述时任务按对应关系同步，未列出db不同步 ;无该字段的情况源与目标db一一对应,无该字段迁移源redis所有db库")
    private Map<Integer,Integer> dbMapper;

    /**
     * 时间偏差
     */
    @Builder.Default
    private Long timeDeviation=0L;

    /**
     * 命令过滤器
     */
    @Builder.Default
    private String commandFilter="";

    /**
     * Key过滤器
     */
    @Builder.Default
    private String keyFilter="";

    @Builder.Default
    private CommandKeyFilterType filterType=CommandKeyFilterType.NONE;

    public Map<Integer, Integer> getDbMapper() {
        if(dbMapper!=null){
            return dbMapper;
        }
        return new HashMap<Integer,Integer>();
    }

}
