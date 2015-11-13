package com.aziis98.vrenderer.api.parser;

import com.aziis98.vrenderer.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

public class PreviewWindow {

    // Static Variables
    public static final Color           windowBackground   = new Color( 0x303030 );
    public static final AffineTransform TRANSFORM_IDENTITY = new AffineTransform();
    public static final String          INFO_BAR           =
            "Drag with the left mouse button to move the image - " +
            "Use the mouse wheel to zoom - " +
            "Right click for the option menu";

    // Fields
    private GeometricCanvas geometricCanvas;
    private BufferedImage preview;
    private JFrame jFrame;

    private double scale        = 1.0;
    private double translationX = 0.0;
    private double translationY = 0.0;

    public PreviewWindow(GeometricCanvas geometricCanvas, BufferedImage initialPreview) {
        this.geometricCanvas = geometricCanvas;
        this.preview = initialPreview;

        // Removes the flickering white resizing the window
        System.setProperty( "sun.awt.noerasebackground", "true" );

        jFrame = new JFrame( "Image Preview" );
        jFrame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        jFrame.setLocationRelativeTo( null );
        jFrame.setSize( preview.getWidth(), preview.getHeight() );
        jFrame.setResizable( true );
        jFrame.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                PreviewWindow.this.resize( getPaneWidth(), getPaneHeight() );
            }
        } );

        //region The Popup Menu

        JPopupMenu popupMenu = new JPopupMenu();

        JMenu     menu;
        JMenuItem menuItem;

        menu = new JMenu( "Zoom" );
        {
            // Zoom > 100%
            menuItem = new JMenuItem( "Reset Zoom" );
            {
                menuItem.addActionListener( e -> scale = 1.0 );
            }
            menu.add( menuItem );

            // Zoom > Reset Translation
            menuItem = new JMenuItem( "Reset Translation" );
            {
                menuItem.addActionListener( e -> translationX = translationY = 0 );
            }
            menu.add( menuItem );

            // Zoom > Reset Translation
            menuItem = new JMenuItem( "Reset Both" );
            {
                menuItem.addActionListener( e -> {
                    scale = 1.0;
                    translationX = translationY = 0;
                } );
            }
            menu.add( menuItem );
        }


        {
            menuItem = new JMenuItem( "PReview Options" );
            menuItem.setEnabled( false );
        }
        popupMenu.add( menuItem );
        popupMenu.addSeparator();
        popupMenu.add( menu );
        {
            menu = new JMenu( "Dimension" );
            {
                menuItem = new JMenuItem( "By Sizes" );
                menuItem.addActionListener( e -> {
                    String response = JOptionPane.showInputDialog( jFrame, "Write the new render dimensions:", preview.getWidth() + "x" + preview.getHeight() );
                    try
                    {
                        String[] spSize = response.trim().split( "x" );

                        int w = Integer.parseInt( spSize[0] );
                        int h = Integer.parseInt( spSize[1] );

                        this.preview = geometricCanvas.renderImage( w, h );
                    }
                    catch (NumberFormatException exception)
                    {
                        JOptionPane.showMessageDialog( jFrame, "Incorrect dimension format!", "Input Error", JOptionPane.ERROR_MESSAGE );
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog( jFrame, "Error:\n    " + exception.toString(), "Error", JOptionPane.ERROR_MESSAGE );
                    }
                } );
                menu.add( menuItem );


                menuItem = new JMenuItem( "By Ratio-Resolution" );
                menuItem.addActionListener( e -> {
                    String response = JOptionPane.showInputDialog( jFrame, "Write the new render dimensions:", "16/9 1080p" );
                    try
                    {
                        String[] spCpmp = response.trim().split( " " );
                        String[] spFraction = spCpmp[0].split( "/" );

                        float w = Integer.parseInt( spFraction[0] );
                        float h = Integer.parseInt( spFraction[1] );

                        this.preview = geometricCanvas.renderImage( w / h, Integer.parseInt( spCpmp[1].replace( "p", "" ) ) );
                    }
                    catch (NumberFormatException exception)
                    {
                        JOptionPane.showMessageDialog( jFrame, "Incorrect dimension format!", "Input Error", JOptionPane.ERROR_MESSAGE );
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog( jFrame, "Error:\n    " + exception.toString(), "Error", JOptionPane.ERROR_MESSAGE );
                    }
                } );
                menu.add( menuItem );
            }
        }
        popupMenu.add( menu );


        //endregion

        Canvas jCanvas = new Canvas() {

            @Override
            public void update(Graphics g) {
                this.paint( g );
            }

            @Override
            public void paint(Graphics g) {
                BufferedImage buffer     = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB );

                Graphics2D    graphics2D = (Graphics2D) buffer.getGraphics();
                {
                    graphics2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
                    graphics2D.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
                }

                PreviewWindow.this.update( graphics2D );
                g.drawImage( buffer, 0, 0, null );

                repaint();
            }
        };

        jCanvas.addMouseListener( new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                showPopup( e );
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup( e );
            }

            private void showPopup(MouseEvent e) {
                if ( e.isPopupTrigger() )
                {
                    popupMenu.show( jCanvas, e.getX(), e.getY() );
                }
            }
        } );

        jCanvas.addMouseMotionListener( new MouseMotionAdapter() {
            int px = 0;
            int py = 0;
            int mx = 0;
            int my = 0;

            @Override
            public void mouseMoved(MouseEvent e) {
                px = mx;
                py = my;
                mx = e.getX();
                my = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                px = mx;
                py = my;
                mx = e.getX();
                my = e.getY();

                if ( SwingUtilities.isLeftMouseButton( e ) )
                {
                    translationX += (mx - px) / scale;
                    translationY += (my - py) / scale;
                }
            }
        } );

        jCanvas.addMouseWheelListener( e -> {
            scale -= e.getPreciseWheelRotation() * 0.2F;
            // scale = scale < getMinZoom() ? getMinZoom() : scale;
        } );

        jFrame.getContentPane().add( jCanvas );
        jFrame.setVisible( true );
    }

    private void update(Graphics2D g) {
        g.setBackground( windowBackground );
        g.clearRect( 0, 0, jFrame.getWidth(), jFrame.getHeight() );

        g.translate( getPaneWidth() / 2, getPaneHeight() / 2 );

        scale = scale < getMinZoom() ? getMinZoom() : scale;

        g.scale( scale, scale );
        g.translate( translationX, translationY );

        g.drawImage( preview, -preview.getWidth() / 2, -preview.getHeight() / 2, null );

        g.setColor( Color.WHITE );
        g.drawRect( -preview.getWidth() / 2 - 2, -preview.getHeight() / 2 - 2, preview.getWidth() + 3, preview.getHeight() + 3 );

        g.setTransform( TRANSFORM_IDENTITY );
        g.setColor( Color.gray.brighter() );
        g.setFont( new Font( "Segoe UI", Font.PLAIN, 15 ) );
        g.drawString( INFO_BAR, 5, getPaneHeight() - 10 );
    }

    private void resize(int width, int height) {

    }

    private int getPaneWidth() {
        return jFrame.getContentPane().getWidth();
    }

    private int getPaneHeight() {
        return jFrame.getContentPane().getHeight();
    }

    private float getMinZoom() {
        float minPane    = Math.min( getPaneWidth() - 100, getPaneHeight() - 100 );
        float minPreview = Math.min( preview.getWidth(), preview.getHeight() );

        return Math.min(minPane / minPreview, 1F);
    }


    public PreviewWindow setPreview(BufferedImage preview) {
        this.preview = preview;
        resize( jFrame.getWidth(), jFrame.getHeight() );

        return this;
    }

}












































