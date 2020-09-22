package cn.enilu.common.code.webflash.plugin.ui;

import cn.enilu.common.code.webflash.plugin.utils.GenerateConfig;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiClass;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.HorizontalBox;
import com.intellij.ui.components.panels.VerticalBox;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by ghost on 2016/4/1.
 */
public class ConfigDialog extends DialogWrapper {
    private JBCheckBox controllerCheckBox;
    private JBCheckBox serviceCheckBox;
    private JBCheckBox repositoryCheckBox;
    private JBCheckBox viewCheckBox;


    private JBCheckBox forceCheckBox;

    private JBLabel baseUriLabel;
    private JBLabel basePackageLabel;
    private JBTextField baseUriTextField;
    private JBTextField basePackageTextField;


    private JBLabel modPackageLabel;
    private JBLabel serPackageLabel;
    private JBLabel ctrPackageLabel;
    private JBLabel repoPackageLabel;

    private JBTextField modPackageTextField;
    private JBTextField serPackageTextField;
    private JBTextField ctrPackageTextField;
    private JBTextField repoPackageTextField;

    private final PsiClass mClass;
    private final String basePackage;
    private final String baseUri;
    private final String modelPackageName;

    public ConfigDialog(final PsiClass psiClass) {
        super(psiClass.getProject());
        String arr[] = psiClass.getQualifiedName().split("\\.");
        String modelName = psiClass.getName();
        basePackage = psiClass.getQualifiedName().replace("."+arr[arr.length-4]+"." + arr[arr.length - 3] +"." + arr[arr.length - 2] + "." + arr[arr.length - 1], "");

        modelPackageName = arr[arr.length - 2];
        baseUri = "/" + arr[arr.length - 2];
        mClass = psiClass;
        setupViews(modelName);
        init();


    }


    private void setupViews(String modelName) {

        setTitle("Generate Model:" + modelName);

        controllerCheckBox = new JBCheckBox("controller", true);
        serviceCheckBox = new JBCheckBox("service", true);
        viewCheckBox = new JBCheckBox("views", true);
        repositoryCheckBox = new JBCheckBox("repository",true);

        forceCheckBox = new JBCheckBox("replace", false);

        baseUriTextField = new JBTextField(baseUri);
        basePackageTextField = new JBTextField(basePackage);
        baseUriLabel = new JBLabel("baseUri:");
        basePackageLabel = new JBLabel("base Package:");

        modPackageLabel = new JBLabel("entities Package:");
        serPackageLabel = new JBLabel("services Package:");
        ctrPackageLabel = new JBLabel("controllers Package:");
        repoPackageLabel = new JBLabel("repositories Package:");

        modPackageTextField = new JBTextField("bean.entity."+modelPackageName);
//        modPackageTextField.disable();
        serPackageTextField = new JBTextField("service."+modelPackageName);
        ctrPackageTextField = new JBTextField("api.controller."+modelPackageName);
        repoPackageTextField = new JBTextField("dao."+modelPackageName);

    }

    @Nullable
    @Override
    protected JComponent createSouthPanel() {
        JComponent southPanel = super.createSouthPanel();
        if (null == southPanel) {
            return null;
        }
        final VerticalBox root = new VerticalBox();
        root.add(baseUriLabel);
        root.add(baseUriTextField);
        root.add(basePackageLabel);
        root.add(basePackageTextField);

        root.add(modPackageLabel);
        root.add(modPackageTextField);
        root.add(serPackageLabel);
        root.add(serPackageTextField);
        root.add(ctrPackageLabel);
        root.add(ctrPackageTextField);
        root.add(repoPackageLabel);
        root.add(repoPackageTextField);

        root.add(forceCheckBox);
        root.add(southPanel);
        return root;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JComponent centerPanel = super.createContentPane();
        final VerticalBox verticalBox = new VerticalBox();
        final HorizontalBox horizontalBox1 = new HorizontalBox();


        horizontalBox1.add(controllerCheckBox);
        horizontalBox1.add(serviceCheckBox);
        horizontalBox1.add(viewCheckBox);
        horizontalBox1.add(repositoryCheckBox);

        verticalBox.add(horizontalBox1);
        centerPanel.add(verticalBox);
        return centerPanel;
    }

    public GenerateConfig getGenerateConfig() {
        GenerateConfig config = new GenerateConfig();
        config.setBasePackage(basePackageTextField.getText().trim());
        config.setBaseUri(baseUriTextField.getText().trim());
        config.setConroller(controllerCheckBox.isSelected());
        config.setService(serviceCheckBox.isSelected());
        config.setView(viewCheckBox.isSelected());
        config.setForce(forceCheckBox.isSelected());
        config.setRepository(repositoryCheckBox.isSelected());

        config.setModelPakName(modPackageTextField.getText().trim());
        config.setServicePakName(serPackageTextField.getText().trim());
        config.setControllerPakName(ctrPackageTextField.getText().trim());
        config.setRepoPakName(repoPackageTextField.getText().trim());

        return config;
    }

}
