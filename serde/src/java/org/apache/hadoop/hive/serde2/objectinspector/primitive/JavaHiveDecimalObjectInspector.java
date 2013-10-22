/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.serde2.objectinspector.primitive;

import java.math.BigInteger;

import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;

public class JavaHiveDecimalObjectInspector
    extends AbstractPrimitiveJavaObjectInspector
    implements SettableHiveDecimalObjectInspector {

  protected JavaHiveDecimalObjectInspector() {
    super(TypeInfoFactory.decimalTypeInfo);
  }

  @Override
  public HiveDecimalWritable getPrimitiveWritableObject(Object o) {
    if (o == null) {
      return null;
    }

    if (o instanceof String) {
      o = HiveDecimal.create((String)o);
      if (o == null) {
        return null;
      }
    }

    return new HiveDecimalWritable((HiveDecimal) o);
  }

  @Override
  public HiveDecimal getPrimitiveJavaObject(Object o) {
    return o == null ? null : (HiveDecimal) o;
  }

  @Override
  public Object set(Object o, byte[] bytes, int scale) {
    return HiveDecimal.create(new BigInteger(bytes), scale);
  }

  @Override
  public Object set(Object o, HiveDecimal t) {
    return t;
  }

  @Override
  public Object set(Object o, HiveDecimalWritable t) {
    return t == null ? null : t.getHiveDecimal();
  }

  @Override
  public Object create(byte[] bytes, int scale) {
    return HiveDecimal.create(new BigInteger(bytes), scale);
  }

  @Override
  public Object create(HiveDecimal t) {
    if (t == null) {
      return null;
    }

    return HiveDecimal.create(t.unscaledValue(), t.scale());
  }

}
