package bcn.alten.altenappmanagement.utils;

import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by alten on 16/11/17.
 */

public class AnimationRenderer {

    private final float INITIAL_POSITION = 0.0f;
    private final float ROTATED_POSITION = 180f;
    public final int ANIMATION_TIME = 400;
    public final float ZERO_POINT_FIVE = 0.5f;

    public void expandGroupArrowRotation(ImageView imageView) {
        final RotateAnimation rotateAnim = new RotateAnimation(INITIAL_POSITION, ROTATED_POSITION,
                RotateAnimation.RELATIVE_TO_SELF, ZERO_POINT_FIVE,
                RotateAnimation.RELATIVE_TO_SELF, ZERO_POINT_FIVE);

        rotateAnim.setDuration(ANIMATION_TIME);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
        
    }

    public void collapseGroupArrowRotation(ImageView imageView) {
        final RotateAnimation rotateAnim = new RotateAnimation(ROTATED_POSITION, INITIAL_POSITION,
                RotateAnimation.RELATIVE_TO_SELF, ZERO_POINT_FIVE,
                RotateAnimation.RELATIVE_TO_SELF, ZERO_POINT_FIVE);

        rotateAnim.setDuration(ANIMATION_TIME);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }
    
}
