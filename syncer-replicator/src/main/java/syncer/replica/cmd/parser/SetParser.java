/*
 * Copyright 2016-2018 Leon Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package syncer.replica.cmd.parser;


import syncer.replica.cmd.CommandParser;
import syncer.replica.cmd.CommandParsers;
import syncer.replica.cmd.impl.ExistType;
import syncer.replica.cmd.impl.SetCommand;
import syncer.replica.cmd.impl.XATType;
import syncer.replica.rdb.datatype.ExpiredType;

import static syncer.replica.util.objectutil.Strings.isEquals;


/**
 * @author Leon Chen
 * @since 2.1.0
 */
public class SetParser implements CommandParser<SetCommand> {

    @Override
    public SetCommand parse(Object[] command) {
        byte[] key = CommandParsers.toBytes(command[1]);
        byte[] value = CommandParsers.toBytes(command[2]);
        int idx = 3;
        ExistType existType = ExistType.NONE;
        Long expiredValue = null;
        XATType xatType = XATType.NONE;
        Long xatValue = null;
        boolean et = false, st = false;
        boolean keepTtl = false;
        boolean get = false;
        ExpiredType expiredType = ExpiredType.NONE;
        while (idx < command.length) {
            String param = CommandParsers.toRune(command[idx++]);
            if (!et && isEquals(param, "NX")) {
                existType = ExistType.NX;
                et = true;
            } else if (!et && isEquals(param, "XX")) {
                existType = ExistType.XX;
                et = true;
            } else if (!keepTtl && isEquals(param, "KEEPTTL")) {
                keepTtl = true;
            } else if (!keepTtl && isEquals(param, "GET")) {
                get = true;
            }

            if (!st && isEquals(param, "EX")) {
                expiredType = ExpiredType.SECOND;
                expiredValue = Long.valueOf(CommandParsers.toRune(command[idx++]));
                st = true;
            } else if (!st && isEquals(param, "PX")) {
                expiredType = ExpiredType.MS;
                expiredValue = Long.valueOf(CommandParsers.toRune(command[idx++]));
                st = true;
            } else if (!st && isEquals(param, "EXAT")) {
                xatType = XATType.EXAT;
                xatValue = Long.valueOf(CommandParsers.toRune(command[idx++]));
                st = true;
            } else if (!st && isEquals(param, "PXAT")) {
                xatType = XATType.PXAT;
                xatValue = Long.valueOf(CommandParsers.toRune(command[idx++]));
                st = true;
            }
        }
        return new SetCommand(key, value, keepTtl, expiredType, expiredValue, xatType, xatValue, existType, get);
    }

}
