package com.paypal.butterfly.api;

import java.io.File;

import com.paypal.butterfly.extensions.api.Extension;
import com.paypal.butterfly.extensions.api.TransformationTemplate;
import com.paypal.butterfly.extensions.api.exception.ButterflyException;
import com.paypal.butterfly.extensions.api.exception.TemplateResolutionException;
import com.paypal.butterfly.extensions.api.upgrade.UpgradePath;

/**
 * Butterfly façade
 *
 * @author facarvalho
 */
public interface ButterflyFacade {

    /**
     * Returns Butterfly version
     *
     * @return Butterfly version
     */
    String getButterflyVersion();

    /**
     * Returns the registered extension, or null, if none has been registered.
     * If multiple extensions have been registered, throw an {@link IllegalStateException}.
     *
     * @throws IllegalStateException if multiple extensions have been registered
     * @return the registered extension
     */
    Extension getRegisteredExtension();

    /**
     * Butterfly might be able to automatically identify the type of application
     * and which transformation template to be applied to it. This automatic
     * transformation template resolution is delegated to the registered
     * Extension class. Based on the application folder, and its content, the
     * registered extension might decide which transformation template should be used
     * to transform it. Only one can be chosen. If no template applies, or if no or multiple
     * extensions have been registered, a {@link TemplateResolutionException} is
     * thrown explaining the reason why no template could be chosen.
     *
     * @param applicationFolder the folder where the code of the application to be transformed is
     * @return the chosen transformation template
     * @throws TemplateResolutionException if no template applies or if no or multiple extension have been registered
     */
    Class<? extends TransformationTemplate> automaticResolution(File applicationFolder) throws TemplateResolutionException;

    /**
     * Creates and returns a new {@link Configuration} object
     * set to apply the transformation against the original application folder
     * and the result will not be compressed to a zip file.
     * <br>
     * Notice that calling this method will result in {@link Configuration#isModifyOriginalFolder()}
     * to return {@code true}.
     *
     * @return a brand new {@link Configuration} object
     */
    Configuration newConfiguration();

    /**
     * Creates and returns a new {@link Configuration} object
     * set to place the transformed application at a new folder at the original application
     * parent folder, besides compressing it to a zip file, depending on {@code zipOutput}.
     * <br>
     * The transformed application folder's name is the same as original folder,
     * plus a "-transformed-yyyyMMddHHmmssSSS" suffix.
     * <br>
     * Notice that calling this method will result in {@link Configuration#isModifyOriginalFolder()}
     * to return {@code false}.
     *
     * @param zipOutput if true, the transformed application folder will be compressed into a zip file
     * @return a brand new {@link Configuration} object
     */
    Configuration newConfiguration(boolean zipOutput);

    /**
     * Creates and returns a new {@link Configuration} object
     * set to place the transformed application at {@code outputFolder},
     * and compress it to a zip file or not, depending on {@code zipOutput}.
     * <br>
     * Notice that calling this method will result in {@link Configuration#isModifyOriginalFolder()}
     * to return {@code false}.
     *
     * @param outputFolder the output folder where the transformed application is
     *                     supposed to be placed
     * @param zipOutput if true, the transformed application folder will be compressed into a zip file
     * @return a brand new {@link Configuration} object
     * @throws IllegalArgumentException if {@code outputFolder} is null, does not exist, or is not a directory
     */
    Configuration newConfiguration(File outputFolder, boolean zipOutput);

    /**
     * Transform an application
     *
     * @param applicationFolder application folder
     * @param templateClassName transformation template class name
     * @return the transformation result object
     * @throws ButterflyException if the template class could not be found
     */
    TransformationResult transform(File applicationFolder, String templateClassName) throws ButterflyException;

    /**
     * Transform an application, and also accept an additional
     * parameter with configuration
     *
     * @param applicationFolder application folder
     * @param templateClassName transformation template class name
     * @param configuration Butterfly configuration object
     * @return the transformation result object
     * @throws ButterflyException if the template class could not be found
     */
    TransformationResult transform(File applicationFolder, String templateClassName, Configuration configuration) throws ButterflyException;

    /**
     * Transform an application
     *
     * @param applicationFolder application folder
     * @param templateClass transformation template class
     * @return the transformation result object
     */
    TransformationResult transform(File applicationFolder, Class<? extends TransformationTemplate> templateClass);

    /**
     * Transform an application, and also accept an additional
     * parameter with configuration
     *
     * @param applicationFolder application folder
     * @param templateClass transformation template class
     * @param configuration Butterfly configuration object
     * @return the transformation result object
     */
    TransformationResult transform(File applicationFolder, Class<? extends TransformationTemplate> templateClass, Configuration configuration);

    /**
     * Upgrade an application based on an upgrade path
     *
     * @param applicationFolder application folder
     * @param upgradePath upgrade path object used to upgrade this application
     * @return the transformation result object
     */
    TransformationResult transform(File applicationFolder, UpgradePath upgradePath);

    /**
     * Transform an application based on an upgrade path, and also accept an additional
     * parameter with configuration
     *
     * @param applicationFolder application folder
     * @param upgradePath upgrade path object used to upgrade this application
     * @param configuration Butterfly configuration object
     * @return the transformation result object
     */
    TransformationResult transform(File applicationFolder, UpgradePath upgradePath, Configuration configuration);

}