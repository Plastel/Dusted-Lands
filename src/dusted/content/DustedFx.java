package dusted.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.graphics.*;
import dusted.type.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.*;

public class DustedFx {
    public static final Rand rand = new Rand();

    public static Effect
    powderLeak = new Effect(46f, 20f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Mathf.lerpDelta(Layer.block - 0.001f, Layer.blockUnder, e.finpow()));
        rand.setSeed(e.id);

        randLenVectors(e.id, 10, e.finpow() * 10f, e.rotation, 22f, (x, y) -> {
            color(Tmp.c1.set(powder.color).mul(rand.random(1f, 1.1f)), Tmp.c2.set(powder.color).mul(rand.random(0.4f, 0.6f)), e.finpow() / 2f);
            Fill.circle(e.x + x, e.y + y, 0.1f + e.fout() * 2f);
        });
    }),

    auraFade = new Effect(30f, e -> {
        color(e.color);
        Lines.dashCircle(e.x, e.y, e.rotation * e.fout(Interp.pow2));
    }),

    blazing = new Effect(40f, e -> {
        color(DustedPal.lightPyreol, DustedPal.darkPyreol, e.fin());

        randLenVectors(e.id, 5, 3f + e.fin() * 9f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
        });
        float w = 1f + e.fout() * 3f;
        randLenVectors(e.id, 3, 5f, (x, y) -> {
            Drawf.tri(e.x, e.y, w, 4f * e.fout(), Mathf.angle(x, y));
            Drawf.tri(e.x, e.y, w, 2f * e.fout(), Mathf.angle(x, y) + 180f);
        });
    }),

    funnelExtractSmall = new Effect(30f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Layer.blockOver);
        color(powder.color);

        for (int i = 0; i < 4; i++) {
            randLenVectors(e.id, 6, e.finpow() * 4f, 45f + i * 90f, 10f, (x, y) -> {
                Fill.circle(e.x + x + Angles.trnsx(Mathf.angle(x, y), 3f), e.y + y + Angles.trnsy(Mathf.angle(x, y), 3f), e.fout() * 1.2f);
            });
        }
    }),

    funnelExtract = new Effect(35f, e -> {
        if (!(e.data instanceof Powder powder)) return;
        Draw.z(Layer.blockOver);
        color(powder.color);

        for (int i = 0; i < 4; i++) {
            randLenVectors(e.id, 8, e.finpow() * 6f, 45f + i * 90f, 15f, (x, y) -> {
                Fill.circle(e.x + x + Angles.trnsx(Mathf.angle(x, y), 6f), e.y + y + Angles.trnsy(Mathf.angle(x, y), 6f), e.fout() * 2f);
            });
        }
    }),

    bounce = new Effect(40f, 120f, e -> {
        if (!(e.data instanceof Float distance)) return;
        float tx = e.x + Angles.trnsx(e.rotation + 180f, distance / 2) + Angles.trnsx(e.rotation, e.finpow() * distance);
        float ty = e.y + Angles.trnsy(e.rotation + 180f, distance / 2) + Angles.trnsy(e.rotation, e.finpow() * distance);
        float width = distance / 4f;
        float height = distance / 10f;

        for (int i : Mathf.signs) {
            color(DustedPal.decayingYellow);
            Drawf.tri(e.x, e.y, distance + 22f, e.foutpow() * (distance / 16f), e.rotation + 90 * i);

            Fill.tri(tx + Angles.trnsx(e.rotation + 140f * i, e.foutpow() * width), ty + Angles.trnsy(e.rotation + 140f * i, e.foutpow() * width), tx + Angles.trnsx(e.rotation, e.foutpow() * height), ty + Angles.trnsy(e.rotation, e.foutpow() * height), tx - Angles.trnsx(e.rotation, e.foutpow() * height), ty - Angles.trnsy(e.rotation, e.foutpow() * height));
        }
    }),

    eruption = new Effect(80f, 1200f, e -> {
        color(Pal.lighterOrange, Pal.lightOrange, Color.gray, e.fin());
        randLenVectors(e.id, 32, 520f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 12f + 0.5f);
        });
        color(Color.gray);
        randLenVectors(e.id, 44, 580f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f);
        });
    }),

    shootLaunch = new Effect(40f, e -> {
        e.scaled(20f, i -> {
            color(Pal.lighterOrange, Pal.lightOrange, Pal.gray, i.fin());
            Lines.stroke(i.fout() * 2f);

            randLenVectors(i.id, 6, i.finpow() * 12f, i.rotation, 20f, (x, y) -> {
                Lines.lineAngle(i.x + x, i.y + y, Mathf.angle(x, y), i.foutpow() * 7f);
            });
        });

        color(Pal.lightishGray, Pal.gray, e.fin());

        randLenVectors(e.id, 8, e.finpow() * 9f, e.rotation + 180f, 50f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
        });
    }),

    hitLaunch = new Effect(20f, e -> {
        color(Color.white, Pal.lightOrange, e.fin());
        Lines.stroke(e.fout() * 2f);

        randLenVectors(e.id, 6, e.finpow() * 9f, e.rotation + 180f, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.foutpow() * 5f);
        });
        randLenVectors(e.id, 8, e.finpow() * 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 3f);
        });

        Lines.circle(e.x, e.y, e.finpow() * 4f);
    }),

    shootCavnenShrapnel = new Effect(20f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());
        Lines.stroke(e.fout() + 0.5f);

        randLenVectors(e.id, 5, 20f * e.finpow(), e.rotation, 30f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 4f + 1f);
        });
    }),

    splitShot = new Effect(40f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());

        randLenVectors(e.id, 7, 18f * e.fin(), e.rotation, 40f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 4f + 2f);
        });
    }),

    orbSummon = new Effect(40f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());
        randLenVectors(e.id, 12, e.finpow() * 12f, e.rotation, 360f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.foutpow() * 2f);
        });
    }),

    shootPowder = new Effect(16f, e -> {
        color(Color.white, e.color, e.fin());
        float w = 1.4f + 6 * e.fout();
        Drawf.tri(e.x, e.y, w, 18f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
    }),

    shootPowderSquares = new Effect(20f, e -> {
        color(Color.white, e.color, e.fin());
        randLenVectors(e.id, 8, e.finpow() * 14f, e.rotation, 25f, (x, y) -> {
            Fill.rect(e.x + x, e.y + y, e.foutpow() * 6f, e.foutpow() * 6f, Mathf.angle(x, y) + e.fin() * 60f);
        });
    }),

    hitPowder = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        e.scaled(10f, s -> {
            Lines.stroke(s.fout() * 2.5f);
            Lines.circle(s.x, s.y, s.finpow() * 6f);
        });

        randLenVectors(e.id, 5, e.finpow() * 8f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f);
        });
    }),

    hitCavnen = new Effect(15f, e -> {
        color(DustedPal.decayingYellow, DustedPal.decayingYellowBack, e.fin());

        e.scaled(10f, s -> {
            Lines.stroke(s.fout() * 2.5f);
            Lines.circle(s.x, s.y, s.finpow() * 6f);
        });

        randLenVectors(e.id, 5, e.finpow() * 8f, (x, y) -> {
            Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 5f);
        });
    }),

    hitPinkLaser =  new Effect(10f, e -> {
        color(Color.white, DustedPal.pinkHeal, e.fin());
        stroke(0.6f + e.fout());
        Lines.circle(e.x, e.y, e.fin() * 4f);

        randLenVectors(e.id, 5, 1f + e.finpow() * 6f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.5f + e.fout());
        });
    }),

    //kinda lazy lmao
    pinkHeal = new Effect(11, e -> {
        color(DustedPal.pinkHeal);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, 2f + e.finpow() * 7f);
    }),

    pinkHealWaveDynamic = new Effect(22f, e -> {
        color(DustedPal.pinkHeal);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, 4f + e.finpow() * e.rotation);
    }),

    //jkdfhgkjshrgiushdjkfckvhiu
    high = new Effect(20f, e -> {
        randLenVectors(e.id, 2, 4f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y + e.finpow(), e.fout() * 2f);
        });
    });
}
