package com.ratons.utils

import at.hannibal2.skyhanni.events.LorenzRenderWorldEvent
import at.hannibal2.skyhanni.utils.RenderUtils
import net.minecraft.util.AxisAlignedBB
import java.awt.Color
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11

object RenderUtils {
    fun LorenzRenderWorldEvent.drawBoundingBox(
        aabb: AxisAlignedBB,
        color: Color,
        lineWidth: Int? = null,
        throughBlocks: Boolean = false,
    ) {
        if (throughBlocks) {
            GlStateManager.disableDepth()
        }
        GlStateManager.disableCull()

        val vp = RenderUtils.getViewerPos(partialTicks)
        val effectiveAABB = AxisAlignedBB(
            aabb.minX - vp.x, aabb.minY - vp.y, aabb.minZ - vp.z,
            aabb.maxX - vp.x, aabb.maxY - vp.y, aabb.maxZ - vp.z,
        )

        GlStateManager.enableBlend()
        GlStateManager.disableLighting()
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0)
        GlStateManager.disableTexture2D()

        with(effectiveAABB) {
            val tessellator = Tessellator.getInstance()
            val worldRenderer = tessellator.worldRenderer

            lineWidth?.let { GL11.glLineWidth(it.toFloat()) }

            with(color) {
                GlStateManager.color(red / 255f, green / 255f, blue / 255f, alpha / 255f)
            }
            // Bottom face
            worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION)
            worldRenderer.pos(minX, minY, minZ).endVertex()
            worldRenderer.pos(maxX, minY, minZ).endVertex()
            worldRenderer.pos(maxX, minY, maxZ).endVertex()
            worldRenderer.pos(minX, minY, maxZ).endVertex()
            tessellator.draw()

            // Top face
            worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION)
            worldRenderer.pos(minX, maxY, maxZ).endVertex()
            worldRenderer.pos(maxX, maxY, maxZ).endVertex()
            worldRenderer.pos(maxX, maxY, minZ).endVertex()
            worldRenderer.pos(minX, maxY, minZ).endVertex()
            tessellator.draw()

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION)
            worldRenderer.pos(minX, minY, minZ).endVertex()
            worldRenderer.pos(minX, maxY, minZ).endVertex()
            worldRenderer.pos(minX, minY, maxZ).endVertex()
            worldRenderer.pos(minX, maxY, maxZ).endVertex()
            worldRenderer.pos(maxX, minY, minZ).endVertex()
            worldRenderer.pos(maxX, maxY, minZ).endVertex()
            worldRenderer.pos(maxX, minY, maxZ).endVertex()
            worldRenderer.pos(maxX, maxY, maxZ).endVertex()
            tessellator.draw()
        }

        GlStateManager.enableTexture2D()
        GlStateManager.disableLighting()
        GlStateManager.disableBlend()
        GlStateManager.enableCull()

        if (throughBlocks) {
            GlStateManager.enableDepth()
        }
    }
}
