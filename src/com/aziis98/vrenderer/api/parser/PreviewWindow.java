package com.aziis98.vrenderer.api.parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class PreviewWindow {

    // Static Variables
    public static final Color windowBackground = new Color( 0x303030 );

    // Fields
    private BufferedImage preview;
    private JFrame jFrame;

    private double scale = 1.0;
    private double translationX = 0.0;
    private double translationY = 0.0;

    public PreviewWindow(BufferedImage preview) {
        this.preview = preview;

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

        JMenu menuZoom = new JMenu( "Zoom" );
        {
            // Zoom > 100%
            JMenuItem menuZoomSet100 = new JMenuItem( "100%" );
            {
                menuZoomSet100.addActionListener( e -> scale = 1F );
            }
            menuZoom.add( menuZoomSet100 );

        }

        popupMenu.add( "Preview Options" );
        popupMenu.addSeparator();
        popupMenu.add( menuZoom );

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

        jCanvas.addMouseWheelListener( new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scale -= e.getPreciseWheelRotation() * 0.2F;

                scale =  scale < getMinZoom() ? getMinZoom() : scale;
            }

            private float getMinZoom() {
                return preview.getWidth() / (float) getPaneWidth();
            }

        } );

        jFrame.getContentPane().add( jCanvas );
        jFrame.setVisible( true );
    }

    private void update(Graphics2D g) {
        g.setBackground( windowBackground );
        g.clearRect( 0, 0, jFrame.getWidth(), jFrame.getHeight() );

        g.translate( getPaneWidth() / 2, getPaneHeight() / 2 );
        g.scale( scale, scale );
        g.translate( translationX, translationY );

        g.drawImage( preview, -preview.getWidth() / 2, -preview.getHeight() / 2, null );

        g.setColor( Color.WHITE );
        g.drawRect( -preview.getWidth() / 2 - 2, -preview.getHeight() / 2 - 2, preview.getWidth() + 3, preview.getHeight() + 3 );
    }

    private void resize(int width, int height) {

    }

    private int getPaneWidth() {
        return jFrame.getContentPane().getWidth();
    }

    private int getPaneHeight() {
        return jFrame.getContentPane().getHeight();
    }


    public PreviewWindow setPreview(BufferedImage preview) {
        this.preview = preview;
        resize( jFrame.getWidth(), jFrame.getHeight() );

        return this;
    }

}












































