package cn.enilu.common.code.webflash.plugin;

import cn.enilu.common.code.webflash.plugin.ui.ErrorDialog;
import cn.enilu.common.code.webflash.plugin.ui.ConfigDialog;
import cn.enilu.common.code.webflash.plugin.utils.GenerateConfig;
import com.google.common.io.Files;
import com.intellij.execution.Executor;
import com.intellij.execution.ExecutorRegistry;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.runners.ExecutionUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.io.File;
import java.nio.charset.Charset;


/**
 * 入口类<br>
 * </p> Copyright by easecredit.com<br>
 * 作者: zhangtao <br>
 * 创建日期: 16-7-24<br>
 */
public class GeneratorAction  extends AnAction {
    Project project=null;
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);

        PsiClass rootClass = getTargetClass(e, editor);
        if (null == rootClass) {
            new ErrorDialog(project).show();
            return;
        }
        ConfigDialog fieldDialog = null;
        try {
             fieldDialog = new ConfigDialog(rootClass);
        }catch (Exception ex){
            new ErrorDialog(project,"error","\npackage名成不规范").show();
            return ;
        }
        fieldDialog.show();
        if (!fieldDialog.isOK()) {
            return;
        }

        generateCode(rootClass, fieldDialog.getGenerateConfig());
    }

    private void generateCode(final PsiClass rootClass,
                              final GenerateConfig generateConfig) {
        RunManager runManager = RunManager.getInstance(project);
        ApplicationConfiguration appConfig = new ApplicationConfiguration("generator", project, ApplicationConfigurationType.getInstance());
        appConfig.setMainClassName("cn.enilu.flash.code.Generator");
        String entityClassName = rootClass.getName();
        StringBuilder programArgs = new StringBuilder();
        programArgs.append("-basePath ").append(project.getBasePath())
                .append(" -i ").append(entityClassName).append(" -u ").append(generateConfig.getBaseUri()).append(" -p ")
                .append(generateConfig.getBasePackage()) .append(generateConfig.isForce() ? " -f" : "")
                .append(" -v all")
                .append(" -mod ").append(generateConfig.getModelPakName())
                .append(" -ctr ").append(generateConfig.getControllerPakName())
                .append(" -sev ").append(generateConfig.getServicePakName())
                .append(" -repo ").append(generateConfig.getRepoPakName())
                .append(generateConfig.isConroller() ? " controller" : "")
                .append(generateConfig.isService() ? " service" : "")
                .append(generateConfig.isView()?" view":"")
        .append(generateConfig.isRepository()?" repository":"");
        appConfig.setProgramParameters(programArgs.toString());
        Module[] modules = ModuleManager.getInstance(project).getModules();
        appConfig.setWorkingDirectory(new File(modules[2].getModuleFilePath()).getParent());
        appConfig.setModule(modules[2]);
        RunnerAndConfigurationSettings configuration = runManager.createConfiguration(appConfig, appConfig.getFactory());
        runManager.addConfiguration(configuration, true);
        Executor executor = ExecutorRegistry.getInstance().getExecutorById(com.intellij.openapi.wm.ToolWindowId.DEBUG);
        ExecutionUtil.runConfiguration(configuration, executor);

    }

    private PsiClass getTargetClass(AnActionEvent e, Editor editor) {
        final PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = file.findElementAt(offset);
        return  PsiTreeUtil.getParentOfType(element, PsiClass.class);
    }

    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        final PsiClass psiClass = getTargetClass(e, editor);
        e.getPresentation().setVisible((null != project && null != editor && null != psiClass &&
                !psiClass.isEnum() && 0 != psiClass.getAllFields().length));
    }

}