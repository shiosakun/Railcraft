/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.blocks.interfaces;

import mods.railcraft.api.charge.Charge;
import mods.railcraft.api.charge.IChargeBlock;

import java.util.Collections;
import java.util.Map;

/**
 * Created by CovertJaguar on 6/14/2017 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ITileCharge {
    default Map<Charge, IChargeBlock.ChargeSpec> getChargeSpec() {
        return Collections.emptyMap();
    }
}
