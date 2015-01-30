package net.asrex.skillful.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import lombok.extern.log4j.Log4j2;
import net.asrex.skillful.PlayerSkillInfo;
import net.asrex.skillful.event.SkillfulClientInfoUpdatedEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

/**
 * Handles incoming {@link SkillInfoMessage} instances, updates the client-side
 * {@link PlayerSkillInfo}, and sends a
 */
@Log4j2
public class SkillInfoHandler
		implements IMessageHandler<SkillInfoMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SkillInfoMessage message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			return null;
		}
		
		PlayerSkillInfo info = PlayerSkillInfo.getClientInfo();
		
		NBTTagCompound tag = message.getInfoTag();
		info.readNBT(tag);
		
		log.debug("Updated client skill info: {}", tag);
		
		MinecraftForge.EVENT_BUS.post(new SkillfulClientInfoUpdatedEvent(info));
		
		return null;
	}

}
