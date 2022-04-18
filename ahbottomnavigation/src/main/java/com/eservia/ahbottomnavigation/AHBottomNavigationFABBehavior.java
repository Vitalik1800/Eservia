package com.eservia.ahbottomnavigation;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 *
 */
public class AHBottomNavigationFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

	private long lastSnackbarUpdate = 0;

	public AHBottomNavigationFABBehavior(int navigationBarHeight) {
	}

	@Override
	public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
		if (dependency != null && dependency instanceof Snackbar.SnackbarLayout) {
			return true;
		} else if (dependency != null && dependency instanceof AHBottomNavigation) {
			return true;
		}
		return super.layoutDependsOn(parent, child, dependency);
	}

	@Override
	public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
		updateFloatingActionButton(child, dependency);
		return super.onDependentViewChanged(parent, child, dependency);
	}

	/**
	 * Update floating action button bottom margin
	 */
	private void updateFloatingActionButton(FloatingActionButton child, View dependency) {
		if (child != null && dependency instanceof Snackbar.SnackbarLayout) {
			lastSnackbarUpdate = System.currentTimeMillis();
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
			int fabDefaultBottomMargin = p.bottomMargin;
			child.setY(dependency.getY() - fabDefaultBottomMargin);
		} else if (child != null && dependency instanceof AHBottomNavigation) {
			// Hack to avoid moving the FAB when the AHBottomNavigation is moving (showing or hiding animation)
			if (System.currentTimeMillis() - lastSnackbarUpdate < 30) {
				return;
			}
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
			int fabDefaultBottomMargin = p.bottomMargin;
			child.setY(dependency.getY() - fabDefaultBottomMargin);
		}
	}

}