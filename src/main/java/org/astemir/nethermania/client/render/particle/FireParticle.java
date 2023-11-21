package org.astemir.nethermania.client.render.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FireParticle extends TextureSheetParticle {

    private SpriteSet sprite;


    public FireParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_, SpriteSet set) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.sprite = set;
        this.friction = 0.96F;
        this.xd = this.xd * (double)0.01F + p_108332_;
        this.yd = this.yd * (double)0.01F + p_108333_;
        this.zd = this.zd * (double)0.01F + p_108334_;
        this.x += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.y += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.z += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
        this.lifetime = 16;
    }




    public ParticleRenderType getRenderType() {
        return NMParticleRenderTypes.VERY_BRIGHT_FLAME;
    }

    public void move(double p_106817_, double p_106818_, double p_106819_) {
        this.setBoundingBox(this.getBoundingBox().move(p_106817_, p_106818_, p_106819_));
        this.setLocationFromBoundingbox();
    }

    @Override
    public void render(VertexConsumer p_107678_, Camera p_107679_, float p_107680_) {
        super.render(p_107678_, p_107679_, p_107680_);
        if (sprite.get(age,lifetime) != null) {
            this.setSpriteFromAge(sprite);
        }
    }

    public float getQuadSize(float p_106824_) {
        float f = ((float)this.age + p_106824_) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    public int getLightColor(float p_106821_) {
        return 255;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_106827_) {
            this.sprite = p_106827_;
        }

        public Particle createParticle(SimpleParticleType p_106838_, ClientLevel p_106839_, double p_106840_, double p_106841_, double p_106842_, double p_106843_, double p_106844_, double p_106845_) {
            FireParticle fireParticle = new FireParticle(p_106839_, p_106840_, p_106841_, p_106842_, p_106843_, p_106844_, p_106845_,sprite);
            fireParticle.pickSprite(sprite);
            return fireParticle;
        }

    }

}
