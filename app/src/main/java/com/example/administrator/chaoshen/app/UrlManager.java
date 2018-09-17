package com.example.administrator.chaoshen.app;

import android.app.Activity;

import com.youth.xframe.utils.log.XLog;

import java.util.Iterator;
import java.util.Stack;

public class UrlManager {
    /**
     * Created by jafir on 16/3/1.
     */

    private static Stack<String> urlStack;
    private Stack<String> deleteStack = new Stack<>();
    private static UrlManager instance;

    private UrlManager() {
    }

    public static synchronized UrlManager getInstance() {
        if (instance == null) {
            instance = new UrlManager();
            if (urlStack == null) {
                urlStack = new Stack<>();
            }
        }
        return instance;
    }

    public Stack<String> getActivityStack() {
        return urlStack;
    }

    /**
     * 返回当前栈顶的activity
     *
     * @return
     */
    public String currentString() {
        if (urlStack.size() == 0) {
            return null;
        }
        String activity = urlStack.lastElement();
        return activity;
    }

    /**
     * 栈内是否包含此url
     *
     * @param cls
     * @return
     */
    public boolean isContains(Class<?> cls) {
        for (String activity : urlStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 栈内是否包含此activity
     *
     * @param a
     * @return
     */
    public boolean isContains(String a) {
        for (String activity : urlStack) {
            if (activity.equals(a)) {
                return true;
            }
        }
        return false;
    }

    /**
     * activity入栈
     * 一般在baseActivity的onCreate里面加入
     *
     * @param activity
     */
    public void pushActivity(String activity) {
        urlStack.add(activity);
        XLog.e(urlStack.size()+"---入展---------" +
                "--appmanager.currentString()--------出站"+activity);
    }


    /**
     * 移除栈顶第一个activity
     */
    public void popTopActivity() {
        String activity = urlStack.lastElement();
        urlStack.pop();
        /*if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }*/
    }

    /**
     * activity出栈
     * 一般在baseActivity的onDestroy里面加入
     */
    public void popActivity(String activity) {
        if (activity != null) {
            urlStack.remove(activity);
        }
        /*if (activity != null) {
            urlStack.remove(urlStack.lastElement());
        }*/


        XLog.e(urlStack.size()+"------------" +
                "--appmanager.currentString()--------出站"+activity);
    }

    /**
     * activity出栈
     * 一般在baseActivity的onDestroy里面加入
     */
   /* public void popActivity(Class<?> cls) {
        XLog.e("--------------appmanager.currentString()--------出站"+cls.getName());
        Activity deleteActivity = null;
        for (Activity activity : urlStack) {
            if (activity.getClass().equals(cls) && !activity.isFinishing()) {
                deleteActivity = activity;
                activity.finish();
            }
        }
        urlStack.remove(deleteActivity);
    }*/


    /**
     * 从栈顶往下移除 直到cls这个activity为止
     * 如： 现有ABCD popAllActivityUntillOne(B.class)
     * 则： 还有AB存在
     * <p>
     * 注意此方法 会把自身也finish掉
     *
     * @param cls
     */
    /*public void popAllActivityUntillOne(Class cls) {
        while (true) {
            Activity activity = currentString();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }*/

    /**
     * 所有的栈元素 除了 cls的留下 其他全部移除
     * 如： 现有ABCD popAllActivityUntillOne(B.class)
     * 则： 只有B存在
     * 注意此方法 会把自身也finish掉
     */
    public void popAllActivityExceptOne(Class cls) {
        //第一种  ConcurrentModificationException
//        for (Activity activity : urlStack) {
//            if (!activity.getClass().equals(cls) && !activity.isFinishing()) {
//                urlStack.remove(activity);
//                activity.finish();
//            }
//        }

        // 第四种 ConcurrentModificationException
//        for (int i = 0; i < ; i++) {

        // 第三种 可行
        Iterator iterator = urlStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (!activity.getClass().equals(cls) && !activity.isFinishing()) {
//                urlStack.remove(activity);
//               注意这里必须要用iterator的remove 上面的则错误
                iterator.remove();
                activity.finish();
            }
        }

        //第四种 可行 稍显复杂
//        for (Activity activity : urlStack) {
//            if (!activity.getClass().equals(cls) && !activity.isFinishing()) {
//                deleteStack.add(activity);
//                activity.finish();
//            }
//        }
//        /**
//         * 这里进行了特殊处理，如果直接在循环里面remove会报
//         * concurrentmodificationexception 错误
//         * 所以，这里用另一个栈加入进去，统一移除
//         */
//        urlStack.removeAll(deleteStack);
//        deleteStack.clear();
        XLog.d("debug", "dsfsaf size+:" + urlStack.size());
    }

    /**
     * 移除所有的activity
     * 退出应用的时候可以调用
     * （非杀死进程）
     */
    public void popAllActivity() {
        /*for (int i = 0; i < urlStack.size(); i++) {
            if (null != urlStack.get(i) && !urlStack.get(i).isFinishing()) {
                urlStack.get(i).finish();
            }
        }*/
        urlStack.clear();
    }


    /**
     * 获得现在栈内还有多少activity
     *
     * @return
     */
    public int getCount() {
        if (urlStack != null) {
            return urlStack.size();
        }
        return 0;
    }
}

