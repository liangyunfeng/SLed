package com.lyf.app.sled.neon;

import android.graphics.Path;

import com.lyf.app.sled.neon.path.BasePath;
import com.lyf.app.sled.neon.path.BearPath;
import com.lyf.app.sled.neon.path.CrownPath;
import com.lyf.app.sled.neon.path.LovePath;
import com.lyf.app.sled.neon.path.MagicStarPath;
import com.lyf.app.sled.neon.path.StarMoonPath;
import com.lyf.app.sled.neon.path.StarPath;
import com.lyf.app.sled.neon.path.SwordPath;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class NeonPath {

    public final static int LOVE = 0;
    public final static int STAR = 1;
    public final static int MAGICSTAR = 2;
    public final static int CROWN = 3;
    public final static int BEAR = 4;
    public final static int STARMOON = 5;
    public final static int SWORD = 6;

    private int curPathKey = LOVE;
    private int oldPathKey;

    private Path mPath;
    private BasePath mBasePath;
    private int mColorId = 0;

    public NeonPath() {
        this(LOVE);
    }

    public NeonPath(int key) {
        curPathKey = key;
    }

    public void setNeonPathKey(int key) {
        curPathKey = key;
    }

    public int getNeonPathKey() {
        return curPathKey;
    }

    public void setPath(Path path) {
        mPath = path;
    }

    public Path getPath() {
        return getBasePath().path();
    }

    public int getColorId() {
        return mColorId;
    }

    public BasePath getBasePath() {
        if (mBasePath == null || oldPathKey != curPathKey) {
            oldPathKey = curPathKey;
            switch (curPathKey) {
                case LOVE:
                    mBasePath = new LovePath();
                    break;
                case STAR:
                    mBasePath = new StarPath();
                    break;
                case MAGICSTAR:
                    mBasePath = new MagicStarPath();
                    break;
                case CROWN:
                    mBasePath = new CrownPath();
                    break;
                case BEAR:
                    mBasePath = new BearPath();
                    break;
                case STARMOON:
                    mBasePath = new StarMoonPath();
                    break;
                case SWORD:
                    mBasePath = new SwordPath();
                    break;
                default:
                    mBasePath = new LovePath();
                    break;
            }
        }
        return mBasePath;
    }
}
